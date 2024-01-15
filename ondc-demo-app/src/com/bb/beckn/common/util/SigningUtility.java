// 
// Decompiled by Procyon v0.5.36
// 

package com.bb.beckn.common.util;

import org.slf4j.LoggerFactory;
import java.util.Enumeration;
import java.io.InputStream;
import java.io.FileInputStream;
import java.security.KeyStore;
import org.bouncycastle.util.encoders.Hex;
import org.bouncycastle.crypto.digests.Blake2bDigest;
import com.bb.beckn.common.model.HeaderParams;
import com.bb.beckn.common.dto.KeyIdDto;
import java.util.HashMap;
import java.util.Map;
import java.security.PublicKey;
import java.security.spec.KeySpec;
import java.security.KeyFactory;
import java.security.spec.X509EncodedKeySpec;
import org.bouncycastle.crypto.params.Ed25519PublicKeyParameters;
import java.security.PrivateKey;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.signers.Ed25519Signer;
import org.bouncycastle.crypto.params.Ed25519PrivateKeyParameters;
import com.bb.beckn.common.exception.ApplicationException;
import com.bb.beckn.common.exception.ErrorCode;
import org.apache.commons.lang3.StringUtils;
import java.util.Base64;
import java.security.Signature;
import com.bb.beckn.common.model.SigningModel;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class SigningUtility
{
    private static final Logger log;
    
    public String generateSignature(final String req, final SigningModel model) {
        String sign = null;
        try {
            if (model.isCertificateUsed()) {
                final PrivateKey privateKey = this.getPrivateKeyFromP12(model);
                final Signature rsa = Signature.getInstance("SHA1withRSA");
                rsa.initSign(privateKey);
                rsa.update(req.getBytes());
                final byte[] str = rsa.sign();
                sign = Base64.getEncoder().encodeToString(str);
            }
            else {
                if (!StringUtils.isNoneBlank(new CharSequence[] { model.getPrivateKey() })) {
                    SigningUtility.log.error("neither certificate nor private key has been set for signature");
                    throw new ApplicationException(ErrorCode.SIGNATURE_ERROR, ErrorCode.SIGNATURE_ERROR.getMessage());
                }
                Ed25519PrivateKeyParameters privateKey = new Ed25519PrivateKeyParameters(Base64.getDecoder().decode(model.getPrivateKey().getBytes()), 0);
				Ed25519Signer sig = new Ed25519Signer();
				sig.init(true, privateKey);
				sig.update(req.getBytes(), 0, req.length());

				byte[] s1 = sig.generateSignature();
				sign = Base64.getEncoder().encodeToString(s1);
            }
        }
        catch (Exception e) {
            SigningUtility.log.error("error while generating the signature", (Throwable)e);
            throw new ApplicationException(ErrorCode.SIGNATURE_ERROR, ErrorCode.SIGNATURE_ERROR.getMessage());
        }
        SigningUtility.log.info("Signature Generated From Data : " + sign);
        return sign;
    }
    
    public boolean verifySignature(final String signature, final String requestData, final String publicKey) throws ApplicationException {
        boolean isVerified = false;
        try {
            final Ed25519PublicKeyParameters publicKeyParams = new Ed25519PublicKeyParameters(Base64.getDecoder().decode(publicKey), 0);
            final Ed25519Signer sv = new Ed25519Signer();
            sv.init(false, (CipherParameters)publicKeyParams);
            sv.update(requestData.getBytes(), 0, requestData.length());
            final byte[] decodedSign = Base64.getDecoder().decode(signature);
            isVerified = sv.verifySignature(decodedSign);
            SigningUtility.log.info("Is signature verified ? {}", (Object)isVerified);
        }
        catch (Exception e) {
            SigningUtility.log.error(e.getMessage());
            e.printStackTrace();
            throw new ApplicationException(e);
        }
        return isVerified;
    }
    
    public boolean verifyWithP12PublicKey(final String signature, final String requestData, final String publicKey) throws ApplicationException {
        boolean isVerified = false;
        try {
            SigningUtility.log.info("Verifying with public key from p12 certificate");
            final byte[] decryptPubKey = Base64.getDecoder().decode(publicKey);
            final X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decryptPubKey);
            final KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            final PublicKey pubKey = keyFactory.generatePublic(keySpec);
            final Signature sig = Signature.getInstance("SHA1withRSA");
            sig.initVerify(pubKey);
            sig.update(requestData.getBytes());
            final byte[] decodedSign = Base64.getDecoder().decode(signature);
            isVerified = sig.verify(decodedSign);
        }
        catch (Exception e) {
            SigningUtility.log.info("exception while verifing p12 certificate signature:", (Throwable)e);
            throw new ApplicationException(e);
        }
        return isVerified;
    }
    
    public Map<String, String> parseAuthorizationHeader(String authHeader) {
        final Map<String, String> holder = new HashMap<String, String>();
        if (authHeader.contains("Signature ")) {
            authHeader = authHeader.replace("Signature ", "");
            final String[] split;
            final String[] keyVals = split = authHeader.split(",");
            for (final String keyVal : split) {
                final String[] parts = keyVal.split("=", 2);
                if (parts[0] != null && parts[1] != null) {
                    holder.put(parts[0].trim(), parts[1].trim());
                }
            }
            return holder;
        }
        return null;
    }
    
    public KeyIdDto splitKeyId(String kid) throws ApplicationException {
        KeyIdDto keyIdDto = null;
        //System.out.println("kid--- "+ kid);
        try {
            if (kid != null && !kid.isEmpty()) {
                kid = kid.replace("\"", "");
                keyIdDto = new KeyIdDto();
                final String[] a = kid.split("[|]");
                keyIdDto.setKeyId(a[0]);
                keyIdDto.setUniqueKeyId(a[1]);
                keyIdDto.setAlgo("ed25519");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(ErrorCode.INVALID_AUTH_HEADER, ErrorCode.INVALID_AUTH_HEADER.getMessage());
        }
        return keyIdDto;
    }
    
    public HeaderParams splitHeadersParam(String headers) throws ApplicationException {
        HeaderParams headerParams = null;
        try {
            if (headers != null && !headers.isEmpty()) {
                headers = headers.replace("\"", "");
                headerParams = new HeaderParams();
                final String[] a = headers.split(" ");
                if (a == null || a.length <= 2) {
                    SigningUtility.log.error("Invalid Header");
                    throw new ApplicationException(ErrorCode.INVALID_AUTH_HEADER, ErrorCode.INVALID_AUTH_HEADER.getMessage());
                }
                headerParams.setCreated(a[0].replace("(", "").replace(")", ""));
                headerParams.setExpires(a[1].replace("(", "").replace(")", ""));
                headerParams.setDiagest(a[2].trim());
                if (headerParams.getCreated() == null || !"created".equalsIgnoreCase(headerParams.getCreated()) || headerParams.getExpires() == null || !"expires".equalsIgnoreCase(headerParams.getExpires()) || headerParams.getDiagest() == null || !"digest".equalsIgnoreCase(headerParams.getDiagest())) {
                    SigningUtility.log.error("Header sequense mismatch");
                    throw new ApplicationException(ErrorCode.HEADER_SEQ_MISMATCH, ErrorCode.HEADER_SEQ_MISMATCH.getMessage());
                }
            }
        }
        catch (Exception e) {
            SigningUtility.log.error("Header parsing Failed");
            throw new ApplicationException(ErrorCode.HEADER_PARSING_FAILED, ErrorCode.HEADER_PARSING_FAILED.getMessage());
        }
        return headerParams;
    }
    
    public String generateBlakeHash(String req) {
		Blake2bDigest blake2bDigest = new Blake2bDigest(512);
		byte[] test = req.getBytes();
		blake2bDigest.update(test, 0, test.length);
		byte[] hash = new byte[blake2bDigest.getDigestSize()];
		blake2bDigest.doFinal(hash, 0);
		String bs64 = Base64.getEncoder().encodeToString(hash);
		// log.info("Base64 URL Encoded : " + bs64);
		return bs64;
	}
    
    /*public String generateBlakeHash(final String req) {
        final Blake2bDigest digest = new Blake2bDigest(512);
        final byte[] test = req.getBytes();
        digest.update(test, 0, test.length);
        final byte[] hash = new byte[digest.getDigestSize()];
        digest.doFinal(hash, 0);
        final String hex = Hex.toHexString(hash);
        return Base64.getUrlEncoder().encodeToString(hex.getBytes());
    }*/
    
    public boolean validateTime(String crt, String exp) {
        boolean isValid = false;
        try {
            if (crt != null && exp != null) {
                crt = crt.replace("\"", "");
                exp = exp.replace("\"", "");
                final long created = Long.parseLong(crt);
                final long expiry = Long.parseLong(exp);
                final long now = System.currentTimeMillis() / 1000L;
                final long diffInSec = expiry - created;
                if (diffInSec > 0L && created <= now && expiry > now && expiry >= created) {
                    isValid = true;
                }
            }
            else {
                SigningUtility.log.error("created or expires timestamp value is null.");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        SigningUtility.log.debug("Is request valid with respect to sign header timestamp? {}", (Object)isValid);
        return isValid;
    }
    
    public PrivateKey getPrivateKeyFromP12(final SigningModel model) {
        SigningUtility.log.info("The SigningModel is: {}", (Object)model);
        final String decodedPwd = new String(Base64.getDecoder().decode(model.getCertificatePwd()));
        SigningUtility.log.info("decoded certificate pwd is: {}", (Object)decodedPwd);
        final char[] pwd = decodedPwd.toCharArray();
        PrivateKey userCertPrivateKey = null;
        try {
            final KeyStore ks = KeyStore.getInstance(model.getCertificateType());
            final String certificateAlias = model.getCertificateAlias();
            final String path = model.getCertificatePath();
            SigningUtility.log.info("certificate complete path is: {}", (Object)path);
            final FileInputStream fileInputStream = new FileInputStream(path);
            ks.load(fileInputStream, pwd);
            final Enumeration<String> e = ks.aliases();
            while (e.hasMoreElements()) {
                final String alias = e.nextElement();
                if (StringUtils.isNoneBlank(new CharSequence[] { certificateAlias }) && alias.equals(certificateAlias.trim())) {
                    userCertPrivateKey = (PrivateKey)ks.getKey(alias, pwd);
                    SigningUtility.log.info("matching certificate alias {} found in the certificate", (Object)certificateAlias);
                    break;
                }
            }
            if (userCertPrivateKey == null) {
                throw new ApplicationException(ErrorCode.CERTIFICATE_ALIAS_ERROR, ErrorCode.CERTIFICATE_ALIAS_ERROR.getMessage());
            }
        }
        catch (Exception e2) {
            SigningUtility.log.error("error while reading the signature from certificate", (Throwable)e2);
            throw new ApplicationException(ErrorCode.CERTIFICATE_ERROR, ErrorCode.CERTIFICATE_ERROR.getMessage());
        }
        return userCertPrivateKey;
    }
    
    static {
        log = LoggerFactory.getLogger((Class)SigningUtility.class);
    }
}

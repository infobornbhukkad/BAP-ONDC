// 
// Decompiled by Procyon v0.5.36
// 

package com.bb.beckn.common.builder;

import org.slf4j.LoggerFactory;
import java.util.Base64;
import org.bouncycastle.util.encoders.Hex;
import org.bouncycastle.crypto.digests.Blake2bDigest;
import org.springframework.http.HttpHeaders;
import com.bb.beckn.common.model.ConfigModel;
import org.springframework.beans.factory.annotation.Autowired;
import com.bb.beckn.common.util.SigningUtility;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class HeaderBuilder
{
    private static final Logger log;
    @Autowired
    private SigningUtility signingUtility;
    
    public HttpHeaders buildHeaders(final String req, final ConfigModel configModel) {
        final HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", "application/json");
        if (configModel.getMatchedApi().isSetAuthorizationHeader()) {
            final String authHeader = this.buildAuthorizationHeader(req, configModel, headers);
            headers.add("Authorization", authHeader);
            HeaderBuilder.log.info("Authorization header added to HttpHeaders");
        }
        else {
            HeaderBuilder.log.info("Authorization header will not be set as its disabled in the config file");
        }
        return headers;
    }
    
    private String buildAuthorizationHeader(final String req, final ConfigModel configModel, final HttpHeaders headers) {
        final long currentTime = System.currentTimeMillis() / 1000L;
        final int headerValidity = configModel.getMatchedApi().getHeaderValidity();
        final String blakeHash = this.generateBlakeHash(req);
        final String signingString = "(created): " + currentTime + "\n(expires): " + (currentTime + headerValidity) + "\ndigest: BLAKE-512=" + blakeHash + "";
        final String signature = this.signingUtility.generateSignature(signingString, configModel.getSigning());
        final String kid =configModel.getSubscriberId() + "|" + configModel.getKeyid() + "|" + "ed25519";
        final String authHeader = "Signature keyId=\"" + kid + "\",algorithm=\"" + "ed25519" + "\", created=\"" + currentTime + "\", expires=\"" + (currentTime + headerValidity) + "\", headers=\"(created) (expires) digest\", signature=\"" + signature + "\"";
        return authHeader;
    }
    
    public String generateBlakeHash(String req) {
		Blake2bDigest blake2bDigest = new Blake2bDigest(512);
		byte[] test = req.getBytes();
		blake2bDigest.update(test, 0, test.length);
		byte[] hash = new byte[blake2bDigest.getDigestSize()];
		blake2bDigest.doFinal(hash, 0);
		String bs64 = Base64.getEncoder().encodeToString(hash);
		return bs64;
	}
    
   /* private String generateBlakeHash(final String req) {
        final Blake2bDigest digest = new Blake2bDigest(512);
        final byte[] test = req.getBytes();
        digest.update(test, 0, test.length);
        final byte[] hash = new byte[digest.getDigestSize()];
        digest.doFinal(hash, 0);
        final String hex = Hex.toHexString(hash);
        return Base64.getUrlEncoder().encodeToString(hex.getBytes());
    }*/
    
    static {
        log = LoggerFactory.getLogger((Class)HeaderBuilder.class);
    }
}

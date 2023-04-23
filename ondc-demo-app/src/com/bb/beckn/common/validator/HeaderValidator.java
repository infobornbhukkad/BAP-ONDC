// 
// Decompiled by Procyon v0.5.36
// 

package com.bb.beckn.common.validator;

import org.slf4j.LoggerFactory;
import com.bb.beckn.common.model.SigningModel;
import com.bb.beckn.common.model.HeaderParams;
import com.bb.beckn.api.model.lookup.LookupResponse;
import java.util.function.Predicate;
import java.util.Objects;
import java.util.stream.Stream;
import com.bb.beckn.common.exception.ApplicationException;
import com.bb.beckn.common.exception.ErrorCode;
import java.util.Map;
import org.apache.commons.collections4.MapUtils;
import com.bb.beckn.common.dto.KeyIdDto;
import org.apache.commons.lang3.StringUtils;
import com.bb.beckn.common.dto.ApplicationDto;
import com.bb.beckn.api.model.lookup.LookupRequest;
import org.springframework.http.HttpHeaders;
import com.bb.beckn.common.service.ApplicationConfigService;
import com.bb.beckn.common.service.LookupService;
import org.springframework.beans.factory.annotation.Autowired;
import com.bb.beckn.common.util.SigningUtility;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class HeaderValidator
{
    private static final Logger log;
    @Autowired
    private SigningUtility signingUtility;
    @Autowired
    private LookupService lookupService;
    @Autowired
    private ApplicationConfigService configService;
    
    public ApplicationDto validateHeader(final String subscriberId, final HttpHeaders httpHeaders, final String requestBody, final LookupRequest request) {
        final ApplicationDto selectDto = new ApplicationDto();
        final String authHeader = httpHeaders.getFirst("Authorization");
        HeaderValidator.log.info("Going to validate header {}", (Object)authHeader);
        final KeyIdDto keyIdDto = this.validateAuthOrProxyAuthHeader(subscriberId, requestBody, request, authHeader, true);
        selectDto.setKeyIdDto(keyIdDto);
        final String proxyAuthHeader = httpHeaders.getFirst("Proxy-Authorization");
        if (StringUtils.isNotBlank((CharSequence)proxyAuthHeader)) {
            HeaderValidator.log.info("Going to validate proxy header {}", (Object)proxyAuthHeader);
            this.validateAuthOrProxyAuthHeader(subscriberId, requestBody, request, proxyAuthHeader, false);
        }
        return selectDto;
    }
    
    private KeyIdDto validateAuthOrProxyAuthHeader(final String subscriberId, final String requestBody, final LookupRequest request, final String authHeader, final boolean isAuthHeader) {
        final Map<String, String> headersMap = this.signingUtility.parseAuthorizationHeader(authHeader);
        if (MapUtils.isEmpty((Map)headersMap)) {
            final ErrorCode errorCode = isAuthHeader ? ErrorCode.INVALID_AUTH_HEADER : ErrorCode.INVALID_PROXY_AUTH_HEADER;
            HeaderValidator.log.error(errorCode.getMessage());
            throw new ApplicationException(errorCode);
        }
       
        final KeyIdDto keyIdDto = this.signingUtility.splitKeyId(headersMap.get("keyId"));
        final String key = keyIdDto.getKeyId() + "|" + keyIdDto.getUniqueKeyId();
        HeaderValidator.log.info("BAP key is {}", (Object)key);
        final LookupResponse lookupResponse = this.lookupService.getProvidersByKeyId(subscriberId, key, request);
        final boolean allMatch = Stream.of(new String[] { keyIdDto.getKeyId(), keyIdDto.getUniqueKeyId(), keyIdDto.getAlgo() }).anyMatch(Objects::isNull);
        if (allMatch) {
            final ErrorCode errorCode2 = isAuthHeader ? ErrorCode.INVALID_KEY_ID_HEADER : ErrorCode.INVALID_PROXY_KEY_ID_HEADER;
            HeaderValidator.log.error(errorCode2.getMessage());
            throw new ApplicationException(errorCode2);
        }
        final String algoParam = headersMap.get("algorithm");
        if (!"ed25519".equals(keyIdDto.getAlgo()) || algoParam == null || !"ed25519".equals(algoParam.replace("\"", ""))) {
            final ErrorCode errorCode3 = isAuthHeader ? ErrorCode.ALGORITHM_MISMATCH : ErrorCode.PROXY_ALGORITHM_MISMATCH;
            HeaderValidator.log.error(errorCode3.getMessage());
            throw new ApplicationException(errorCode3);
        }
        final HeaderParams headerParams = this.signingUtility.splitHeadersParam(headersMap.get("headers"));
        if (headerParams == null) {
            final ErrorCode errorCode4 = isAuthHeader ? ErrorCode.INVALID_HEADERS_PARAM : ErrorCode.INVALID_PROXY_HEADERS_PARAM;
            HeaderValidator.log.error(errorCode4.getMessage());
            throw new ApplicationException(errorCode4);
        }
        if (!this.signingUtility.validateTime(headersMap.get("created"), headersMap.get("expires"))) {
            final ErrorCode errorCode4 = isAuthHeader ? ErrorCode.REQUEST_EXPIRED : ErrorCode.PROXY_REQUEST_EXPIRED;
            HeaderValidator.log.error(errorCode4.getMessage());
            throw new ApplicationException(errorCode4);
        }
        HeaderValidator.log.info("checking lookup for the key {}", (Object)key);
        if (lookupResponse == null || lookupResponse.getSigningPublicKey() == null) {
            final ErrorCode errorCode4 = isAuthHeader ? ErrorCode.AUTH_FAILED : ErrorCode.PROXY_AUTH_FAILED;
            HeaderValidator.log.error(errorCode4.getMessage());
            throw new ApplicationException(errorCode4);
        }
        /*final SigningModel signingModel = this.configService.getSigningConfiguration(subscriberId);
        if (signingModel.isCertificateUsed()) {
            this.verifySignatureUsingCertificate(headersMap, lookupResponse, requestBody);
        }
        else {
            this.verifySignatureUsingPublicKey(headersMap, lookupResponse, requestBody);
        }*/
        this.verifySignatureUsingPublicKey(headersMap, lookupResponse, requestBody);
        return keyIdDto;
    }
    
    private void verifySignatureUsingCertificate(final Map<String, String> headersMap, final LookupResponse lookupResponse, final String requestBody) {
        final String signed = this.recreateSignedString(headersMap, requestBody);
        if (!this.signingUtility.verifyWithP12PublicKey(headersMap.get("signature").replace("\"", ""), signed, lookupResponse.getSigningPublicKey())) {
            HeaderValidator.log.error(ErrorCode.SIGNATURE_VERIFICATION_FAILED.toString());
            throw new ApplicationException(ErrorCode.SIGNATURE_VERIFICATION_FAILED);
        }
    }
    
    private void verifySignatureUsingPublicKey(final Map<String, String> headersMap, final LookupResponse lookupResponse, final String requestBody) {
       // System.out.println("inside method verifySignatureUsingPublicKey     --" );
    	final String signed = this.recreateSignedString(headersMap, requestBody);
    	//System.out.println("after method verifySignatureUsingPublicKey     --" );
        if (!this.signingUtility.verifySignature(headersMap.get("signature").replace("\"", ""), signed, lookupResponse.getSigningPublicKey())) {
            HeaderValidator.log.error(ErrorCode.SIGNATURE_VERIFICATION_FAILED.toString());
            throw new ApplicationException(ErrorCode.SIGNATURE_VERIFICATION_FAILED);
        }
    }
    
    private String recreateSignedString(final Map<String, String> headersMap, final String requestBody) {
        final String reqBleckHash = this.signingUtility.generateBlakeHash(requestBody);
        final StringBuilder sb = new StringBuilder();
        sb.append("(created): ");
        sb.append(headersMap.get("created").replace("\"", ""));
        sb.append("\n");
        sb.append("(expires): ");
        sb.append(headersMap.get("expires").replace("\"", ""));
        sb.append("\n");
        sb.append("digest: ");
        sb.append("BLAKE-512=" + reqBleckHash);
        return sb.toString();
    }
    
    static {
        log = LoggerFactory.getLogger((Class)HeaderValidator.class);
    }
}

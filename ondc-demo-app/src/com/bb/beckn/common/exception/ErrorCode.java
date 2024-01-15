// 
// Decompiled by Procyon v0.5.36
// 

package com.bb.beckn.common.exception;

public enum ErrorCode
{
    AUTH_FAILED(1001, "Authentication failed"), 
    INVALID_KEY_ID_HEADER(1002, "Invalid keys are found in the header"), 
    INVALID_AUTH_HEADER(1003, "The auth header is not valid"), 
    BAD_REQUEST(1004, "Bad request"), 
    ALGORITHM_MISMATCH(1005, "There is mismatch in the algorithm"), 
    REQUEST_EXPIRED(1006, "The request has expired"), 
    INVALID_HEADERS_PARAM(1007, "Invalid headers are present in header parameters"), 
    SIGNATURE_VERIFICATION_FAILED(1008, "The signature verification has failed"), 
    REQ_DIGEST_MISMATCH(1009, "Request digest mismatch"), 
    INVALID_REQUEST(1010, "Invalid request"), 
    REQUEST_ALREADY_IN_PROCESS(1011, "The request is already in process"), 
    INVALID_DOMAIN(1012, "Invalid domain"), 
    UNKNOWN_ERROR(1013, "The Service faced a fatal technical exception"), 
    REQUEST_ALREADY_PROCESSED(1014, "The request is already processed"), 
    REQUESTED_TRANSACTION_IS_NOT_EXIST(1015, "Transaction id in the request in not valid"), 
    INVALID_ACTION(1016, "Invalid action"), 
    INVALID_CONTENT_TYPE(1017, "Invalid content type. Only application/json allowed"), 
    HEADER_SEQ_MISMATCH(1018, "Header sequence mismatched"), 
    HEADER_PARSING_FAILED(1019, "Header parsing failed"), 
    INVALID_BPP(1020, "BPP is not valid/not registered in NSDL system"), 
    JSON_PROCESSING_ERROR(1021, "Issue while preparing the json"), 
    INVALID_ENTITY_TYPE(1022, "Invalid entity type configured in config file"), 
    HTTP_TIMEOUT_ERROR(1023, "Http timeout error"), 
    NETWORK_ERROR(1024, "Issue while making http call"), 
    CERTIFICATE_ALIAS_ERROR(1025, "Required alias not found in the certificate"), 
    CERTIFICATE_ERROR(1026, "Error while loading the certificate"), 
    SIGNATURE_ERROR(1027, "Error while generating the signature"), 
    PROXY_AUTH_FAILED(2001, "Proxy Authentication failed"), 
    INVALID_PROXY_KEY_ID_HEADER(2002, "Invalid proxy keyid is found in the header"), 
    INVALID_PROXY_AUTH_HEADER(2003, "The proxy auth header is not valid"), 
    PROXY_ALGORITHM_MISMATCH(2005, "There is mismatch in the algorithm of proxy header"), 
    PROXY_REQUEST_EXPIRED(2006, "The proxy request has expired"), 
    INVALID_PROXY_HEADERS_PARAM(2007, "Invalid headers are present in proxy header parameters");
    
    private int code;
    private String message;
    
    private ErrorCode(final int errorCode, final String errorMessage) {
        this.code = errorCode;
        this.message = errorMessage;
    }
    
    public int getCode() {
        return this.code;
    }
    
    public void setCode(final int errorCode) {
        this.code = errorCode;
    }
    
    public String getMessage() {
        return this.message;
    }
    
    public void setMessage(final String errorMessage) {
        this.message = errorMessage;
    }
    
    private static /* synthetic */ ErrorCode[] $values() {
        return new ErrorCode[] { ErrorCode.AUTH_FAILED, ErrorCode.INVALID_KEY_ID_HEADER, ErrorCode.INVALID_AUTH_HEADER, ErrorCode.BAD_REQUEST, ErrorCode.ALGORITHM_MISMATCH, ErrorCode.REQUEST_EXPIRED, ErrorCode.INVALID_HEADERS_PARAM, ErrorCode.SIGNATURE_VERIFICATION_FAILED, ErrorCode.REQ_DIGEST_MISMATCH, ErrorCode.INVALID_REQUEST, ErrorCode.REQUEST_ALREADY_IN_PROCESS, ErrorCode.INVALID_DOMAIN, ErrorCode.UNKNOWN_ERROR, ErrorCode.REQUEST_ALREADY_PROCESSED, ErrorCode.REQUESTED_TRANSACTION_IS_NOT_EXIST, ErrorCode.INVALID_ACTION, ErrorCode.INVALID_CONTENT_TYPE, ErrorCode.HEADER_SEQ_MISMATCH, ErrorCode.HEADER_PARSING_FAILED, ErrorCode.INVALID_BPP, ErrorCode.JSON_PROCESSING_ERROR, ErrorCode.INVALID_ENTITY_TYPE, ErrorCode.HTTP_TIMEOUT_ERROR, ErrorCode.NETWORK_ERROR, ErrorCode.CERTIFICATE_ALIAS_ERROR, ErrorCode.CERTIFICATE_ERROR, ErrorCode.SIGNATURE_ERROR, ErrorCode.PROXY_AUTH_FAILED, ErrorCode.INVALID_PROXY_KEY_ID_HEADER, ErrorCode.INVALID_PROXY_AUTH_HEADER, ErrorCode.PROXY_ALGORITHM_MISMATCH, ErrorCode.PROXY_REQUEST_EXPIRED, ErrorCode.INVALID_PROXY_HEADERS_PARAM };
    }
}

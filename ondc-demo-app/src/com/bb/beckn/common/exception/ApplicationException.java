// 
// Decompiled by Procyon v0.5.36
// 

package com.bb.beckn.common.exception;

public class ApplicationException extends RuntimeException
{
    private static final long serialVersionUID = 4606666126597266141L;
    private final ErrorCode errorCode;
    
    public ApplicationException(final ErrorCode errorCode, final String message, final Throwable cause) {
        super(errorCode.getCode() + "|" + message, cause);
        this.errorCode = errorCode;
    }
    
    public ApplicationException(final ErrorCode errorCode, final String message) {
        super(errorCode.getCode() + "|" + message, null);
        this.errorCode = errorCode;
    }
    
    public ApplicationException(final ErrorCode errorCode) {
        super(String.valueOf(errorCode.getCode()), new Throwable(errorCode.getMessage()));
        this.errorCode = errorCode;
    }
    
    public ApplicationException(final int statusCode, final String message) {
        super(statusCode + "|" + message, null);
        this.errorCode = null;
    }
    
    public ApplicationException(final Throwable cause) {
        super(cause.getMessage(), cause);
        this.errorCode = null;
    }
    
    public ErrorCode getErrorCode() {
        return this.errorCode;
    }
}

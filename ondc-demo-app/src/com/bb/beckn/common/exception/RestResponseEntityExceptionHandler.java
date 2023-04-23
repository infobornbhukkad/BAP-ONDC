// 
// Decompiled by Procyon v0.5.36
// 

package com.bb.beckn.common.exception;

import org.slf4j.LoggerFactory;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.util.concurrent.TimeoutException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler
{
    private static final Logger log;
    private static final String BODY_ERRO_RESPONSE = "Something went wrong";
    
    @ExceptionHandler({ Throwable.class, RuntimeException.class })
    protected ResponseEntity<Object> handleConflict(final Exception ex, final WebRequest request) {
        RestResponseEntityExceptionHandler.log.info("The excpetion caught is:", (Throwable)ex);
        final HttpHeaders headers = new HttpHeaders();
        if (ex instanceof ApplicationException) {
            RestResponseEntityExceptionHandler.log.error("The error is of type ApplicationException");
            final ApplicationException appEx = (ApplicationException)ex;
            if (ErrorCode.AUTH_FAILED.getCode() == appEx.getErrorCode().getCode()) {
                RestResponseEntityExceptionHandler.log.error("Auth failed");
                return (ResponseEntity<Object>)this.handleExceptionInternal(ex, (Object)ErrorCode.AUTH_FAILED.getMessage(), headers, HttpStatus.UNAUTHORIZED, request);
            }
            final String errorMessage = this.buildErrorMessage(ex);
            RestResponseEntityExceptionHandler.log.info("error message to return is {}", (Object)errorMessage);
            return (ResponseEntity<Object>)this.handleExceptionInternal(ex, (Object)errorMessage, headers, HttpStatus.BAD_REQUEST, request);
        }
        else {
            if (ex instanceof WebClientRequestException) {
                RestResponseEntityExceptionHandler.log.error("The error is of type WebClientRequestException");
                final WebClientRequestException requestExp = (WebClientRequestException)ex;
                return (ResponseEntity<Object>)this.handleExceptionInternal(ex, (Object)requestExp.getMessage(), headers, HttpStatus.INTERNAL_SERVER_ERROR, request);
            }
            if (ex instanceof WebClientResponseException) {
                RestResponseEntityExceptionHandler.log.error("The error is of type WebClientResponseException");
                final WebClientResponseException responseExp = (WebClientResponseException)ex;
                return (ResponseEntity<Object>)this.handleExceptionInternal(ex, (Object)responseExp.getMessage(), headers, responseExp.getStatusCode(), request);
            }
            if (ex instanceof TimeoutException) {
                RestResponseEntityExceptionHandler.log.error("The error is of type TimeoutException");
                final TimeoutException timeoutExp = (TimeoutException)ex;
                return (ResponseEntity<Object>)this.handleExceptionInternal(ex, (Object)timeoutExp.getMessage(), headers, HttpStatus.REQUEST_TIMEOUT, request);
            }
            RestResponseEntityExceptionHandler.log.error("Generic error will be thrown");
            return (ResponseEntity<Object>)this.handleExceptionInternal(ex, (Object)"Something went wrong", headers, HttpStatus.INTERNAL_SERVER_ERROR, request);
        }
    }
    
    private String buildErrorMessage(final Exception ex) {
        if (NumberUtils.isDigits(ex.getMessage())) {
            final ErrorCode errorCode = ((ApplicationException)ex).getErrorCode();
            return errorCode.getCode() + "|" + errorCode.getMessage();
        }
        return ex.getMessage();
    }
    
    static {
        log = LoggerFactory.getLogger((Class)RestResponseEntityExceptionHandler.class);
    }
}

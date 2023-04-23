// 
// Decompiled by Procyon v0.5.36
// 

package com.bb.beckn.common.model;

import org.springframework.http.HttpHeaders;

public class HttpModel
{
    private HttpHeaders requestHeaders;
    private String requestBody;
    
    public HttpHeaders getRequestHeaders() {
        return this.requestHeaders;
    }
    
    public String getRequestBody() {
        return this.requestBody;
    }
    
    public void setRequestHeaders(final HttpHeaders requestHeaders) {
        this.requestHeaders = requestHeaders;
    }
    
    public void setRequestBody(final String requestBody) {
        this.requestBody = requestBody;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof HttpModel)) {
            return false;
        }
        final HttpModel other = (HttpModel)o;
        if (!other.canEqual(this)) {
            return false;
        }
        final Object this$requestHeaders = this.getRequestHeaders();
        final Object other$requestHeaders = other.getRequestHeaders();
        Label_0065: {
            if (this$requestHeaders == null) {
                if (other$requestHeaders == null) {
                    break Label_0065;
                }
            }
            else if (this$requestHeaders.equals(other$requestHeaders)) {
                break Label_0065;
            }
            return false;
        }
        final Object this$requestBody = this.getRequestBody();
        final Object other$requestBody = other.getRequestBody();
        if (this$requestBody == null) {
            if (other$requestBody == null) {
                return true;
            }
        }
        else if (this$requestBody.equals(other$requestBody)) {
            return true;
        }
        return false;
    }
    
    protected boolean canEqual(final Object other) {
        return other instanceof HttpModel;
    }
    
    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $requestHeaders = this.getRequestHeaders();
        result = result * 59 + (($requestHeaders == null) ? 43 : $requestHeaders.hashCode());
        final Object $requestBody = this.getRequestBody();
        result = result * 59 + (($requestBody == null) ? 43 : $requestBody.hashCode());
        return result;
    }
    
    @Override
    public String toString() {
        return "HttpModel(requestHeaders=" + this.getRequestHeaders() + ", requestBody=" + this.getRequestBody() + ")";
    }
}

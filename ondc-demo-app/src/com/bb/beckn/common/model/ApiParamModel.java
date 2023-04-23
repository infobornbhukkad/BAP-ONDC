// 
// Decompiled by Procyon v0.5.36
// 

package com.bb.beckn.common.model;

import java.io.Serializable;

public class ApiParamModel implements Serializable
{
    private static final long serialVersionUID = 7461538270839107324L;
    private String name;
    private boolean appendApiName;
    private String httpEntityEndpoint;
    private int httpTimeout;
    private int httpRetryCount;
    private int headerValidity;
    private boolean headerAuthentication;
    private boolean setAuthorizationHeader;
    
    public String getName() {
        return this.name;
    }
    
    public boolean isAppendApiName() {
        return this.appendApiName;
    }
    
    public String getHttpEntityEndpoint() {
        return this.httpEntityEndpoint;
    }
    
    public int getHttpTimeout() {
        return this.httpTimeout;
    }
    
    public int getHttpRetryCount() {
        return this.httpRetryCount;
    }
    
    public int getHeaderValidity() {
        return this.headerValidity;
    }
    
    public boolean isHeaderAuthentication() {
        return this.headerAuthentication;
    }
    
    public boolean isSetAuthorizationHeader() {
        return this.setAuthorizationHeader;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public void setAppendApiName(final boolean appendApiName) {
        this.appendApiName = appendApiName;
    }
    
    public void setHttpEntityEndpoint(final String httpEntityEndpoint) {
        this.httpEntityEndpoint = httpEntityEndpoint;
    }
    
    public void setHttpTimeout(final int httpTimeout) {
        this.httpTimeout = httpTimeout;
    }
    
    public void setHttpRetryCount(final int httpRetryCount) {
        this.httpRetryCount = httpRetryCount;
    }
    
    public void setHeaderValidity(final int headerValidity) {
        this.headerValidity = headerValidity;
    }
    
    public void setHeaderAuthentication(final boolean headerAuthentication) {
        this.headerAuthentication = headerAuthentication;
    }
    
    public void setSetAuthorizationHeader(final boolean setAuthorizationHeader) {
        this.setAuthorizationHeader = setAuthorizationHeader;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ApiParamModel)) {
            return false;
        }
        final ApiParamModel other = (ApiParamModel)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.isAppendApiName() != other.isAppendApiName()) {
            return false;
        }
        if (this.getHttpTimeout() != other.getHttpTimeout()) {
            return false;
        }
        if (this.getHttpRetryCount() != other.getHttpRetryCount()) {
            return false;
        }
        if (this.getHeaderValidity() != other.getHeaderValidity()) {
            return false;
        }
        if (this.isHeaderAuthentication() != other.isHeaderAuthentication()) {
            return false;
        }
        if (this.isSetAuthorizationHeader() != other.isSetAuthorizationHeader()) {
            return false;
        }
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        Label_0143: {
            if (this$name == null) {
                if (other$name == null) {
                    break Label_0143;
                }
            }
            else if (this$name.equals(other$name)) {
                break Label_0143;
            }
            return false;
        }
        final Object this$httpEntityEndpoint = this.getHttpEntityEndpoint();
        final Object other$httpEntityEndpoint = other.getHttpEntityEndpoint();
        if (this$httpEntityEndpoint == null) {
            if (other$httpEntityEndpoint == null) {
                return true;
            }
        }
        else if (this$httpEntityEndpoint.equals(other$httpEntityEndpoint)) {
            return true;
        }
        return false;
    }
    
    protected boolean canEqual(final Object other) {
        return other instanceof ApiParamModel;
    }
    
    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * 59 + (this.isAppendApiName() ? 79 : 97);
        result = result * 59 + this.getHttpTimeout();
        result = result * 59 + this.getHttpRetryCount();
        result = result * 59 + this.getHeaderValidity();
        result = result * 59 + (this.isHeaderAuthentication() ? 79 : 97);
        result = result * 59 + (this.isSetAuthorizationHeader() ? 79 : 97);
        final Object $name = this.getName();
        result = result * 59 + (($name == null) ? 43 : $name.hashCode());
        final Object $httpEntityEndpoint = this.getHttpEntityEndpoint();
        result = result * 59 + (($httpEntityEndpoint == null) ? 43 : $httpEntityEndpoint.hashCode());
        return result;
    }
    
    @Override
    public String toString() {
        return "ApiParamModel(name=" + this.getName() + ", appendApiName=" + this.isAppendApiName() + ", httpEntityEndpoint=" + this.getHttpEntityEndpoint() + ", httpTimeout=" + this.getHttpTimeout() + ", httpRetryCount=" + this.getHttpRetryCount() + ", headerValidity=" + this.getHeaderValidity() + ", headerAuthentication=" + this.isHeaderAuthentication() + ", setAuthorizationHeader=" + this.isSetAuthorizationHeader() + ")";
    }
}

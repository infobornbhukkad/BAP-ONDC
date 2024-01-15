// 
// Decompiled by Procyon v0.5.36
// 

package com.bb.beckn.common.model;

public class AuthDetailsModel
{
    private Integer status;
    private String msg;
    private Integer regId;
    private String publicCert;
    private String keyId;
    
    public Integer getStatus() {
        return this.status;
    }
    
    public String getMsg() {
        return this.msg;
    }
    
    public Integer getRegId() {
        return this.regId;
    }
    
    public String getPublicCert() {
        return this.publicCert;
    }
    
    public String getKeyId() {
        return this.keyId;
    }
    
    public void setStatus(final Integer status) {
        this.status = status;
    }
    
    public void setMsg(final String msg) {
        this.msg = msg;
    }
    
    public void setRegId(final Integer regId) {
        this.regId = regId;
    }
    
    public void setPublicCert(final String publicCert) {
        this.publicCert = publicCert;
    }
    
    public void setKeyId(final String keyId) {
        this.keyId = keyId;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof AuthDetailsModel)) {
            return false;
        }
        final AuthDetailsModel other = (AuthDetailsModel)o;
        if (!other.canEqual(this)) {
            return false;
        }
        final Object this$status = this.getStatus();
        final Object other$status = other.getStatus();
        Label_0065: {
            if (this$status == null) {
                if (other$status == null) {
                    break Label_0065;
                }
            }
            else if (this$status.equals(other$status)) {
                break Label_0065;
            }
            return false;
        }
        final Object this$regId = this.getRegId();
        final Object other$regId = other.getRegId();
        Label_0102: {
            if (this$regId == null) {
                if (other$regId == null) {
                    break Label_0102;
                }
            }
            else if (this$regId.equals(other$regId)) {
                break Label_0102;
            }
            return false;
        }
        final Object this$msg = this.getMsg();
        final Object other$msg = other.getMsg();
        Label_0139: {
            if (this$msg == null) {
                if (other$msg == null) {
                    break Label_0139;
                }
            }
            else if (this$msg.equals(other$msg)) {
                break Label_0139;
            }
            return false;
        }
        final Object this$publicCert = this.getPublicCert();
        final Object other$publicCert = other.getPublicCert();
        Label_0176: {
            if (this$publicCert == null) {
                if (other$publicCert == null) {
                    break Label_0176;
                }
            }
            else if (this$publicCert.equals(other$publicCert)) {
                break Label_0176;
            }
            return false;
        }
        final Object this$keyId = this.getKeyId();
        final Object other$keyId = other.getKeyId();
        if (this$keyId == null) {
            if (other$keyId == null) {
                return true;
            }
        }
        else if (this$keyId.equals(other$keyId)) {
            return true;
        }
        return false;
    }
    
    protected boolean canEqual(final Object other) {
        return other instanceof AuthDetailsModel;
    }
    
    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $status = this.getStatus();
        result = result * 59 + (($status == null) ? 43 : $status.hashCode());
        final Object $regId = this.getRegId();
        result = result * 59 + (($regId == null) ? 43 : $regId.hashCode());
        final Object $msg = this.getMsg();
        result = result * 59 + (($msg == null) ? 43 : $msg.hashCode());
        final Object $publicCert = this.getPublicCert();
        result = result * 59 + (($publicCert == null) ? 43 : $publicCert.hashCode());
        final Object $keyId = this.getKeyId();
        result = result * 59 + (($keyId == null) ? 43 : $keyId.hashCode());
        return result;
    }
    
    @Override
    public String toString() {
        return "AuthDetailsModel(status=" + this.getStatus() + ", msg=" + this.getMsg() + ", regId=" + this.getRegId() + ", publicCert=" + this.getPublicCert() + ", keyId=" + this.getKeyId() + ")";
    }
}

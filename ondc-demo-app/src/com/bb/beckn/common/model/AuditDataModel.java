// 
// Decompiled by Procyon v0.5.36
// 

package com.bb.beckn.common.model;

import java.time.LocalDateTime;

public class AuditDataModel
{
    private String messageId;
    private String transactionId;
    private String action;
    private String domain;
    private String coreVersion;
    private LocalDateTime createdOn;
    private String json;
    private String status;
    
    public String getMessageId() {
        return this.messageId;
    }
    
    public String getTransactionId() {
        return this.transactionId;
    }
    
    public String getAction() {
        return this.action;
    }
    
    public String getDomain() {
        return this.domain;
    }
    
    public String getCoreVersion() {
        return this.coreVersion;
    }
    
    public LocalDateTime getCreatedOn() {
        return this.createdOn;
    }
    
    public String getJson() {
        return this.json;
    }
    
    public String getStatus() {
        return this.status;
    }
    
    public void setMessageId(final String messageId) {
        this.messageId = messageId;
    }
    
    public void setTransactionId(final String transactionId) {
        this.transactionId = transactionId;
    }
    
    public void setAction(final String action) {
        this.action = action;
    }
    
    public void setDomain(final String domain) {
        this.domain = domain;
    }
    
    public void setCoreVersion(final String coreVersion) {
        this.coreVersion = coreVersion;
    }
    
    public void setCreatedOn(final LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }
    
    public void setJson(final String json) {
        this.json = json;
    }
    
    public void setStatus(final String status) {
        this.status = status;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof AuditDataModel)) {
            return false;
        }
        final AuditDataModel other = (AuditDataModel)o;
        if (!other.canEqual(this)) {
            return false;
        }
        final Object this$messageId = this.getMessageId();
        final Object other$messageId = other.getMessageId();
        Label_0065: {
            if (this$messageId == null) {
                if (other$messageId == null) {
                    break Label_0065;
                }
            }
            else if (this$messageId.equals(other$messageId)) {
                break Label_0065;
            }
            return false;
        }
        final Object this$transactionId = this.getTransactionId();
        final Object other$transactionId = other.getTransactionId();
        Label_0102: {
            if (this$transactionId == null) {
                if (other$transactionId == null) {
                    break Label_0102;
                }
            }
            else if (this$transactionId.equals(other$transactionId)) {
                break Label_0102;
            }
            return false;
        }
        final Object this$action = this.getAction();
        final Object other$action = other.getAction();
        Label_0139: {
            if (this$action == null) {
                if (other$action == null) {
                    break Label_0139;
                }
            }
            else if (this$action.equals(other$action)) {
                break Label_0139;
            }
            return false;
        }
        final Object this$domain = this.getDomain();
        final Object other$domain = other.getDomain();
        Label_0176: {
            if (this$domain == null) {
                if (other$domain == null) {
                    break Label_0176;
                }
            }
            else if (this$domain.equals(other$domain)) {
                break Label_0176;
            }
            return false;
        }
        final Object this$coreVersion = this.getCoreVersion();
        final Object other$coreVersion = other.getCoreVersion();
        Label_0213: {
            if (this$coreVersion == null) {
                if (other$coreVersion == null) {
                    break Label_0213;
                }
            }
            else if (this$coreVersion.equals(other$coreVersion)) {
                break Label_0213;
            }
            return false;
        }
        final Object this$createdOn = this.getCreatedOn();
        final Object other$createdOn = other.getCreatedOn();
        Label_0250: {
            if (this$createdOn == null) {
                if (other$createdOn == null) {
                    break Label_0250;
                }
            }
            else if (this$createdOn.equals(other$createdOn)) {
                break Label_0250;
            }
            return false;
        }
        final Object this$json = this.getJson();
        final Object other$json = other.getJson();
        Label_0287: {
            if (this$json == null) {
                if (other$json == null) {
                    break Label_0287;
                }
            }
            else if (this$json.equals(other$json)) {
                break Label_0287;
            }
            return false;
        }
        final Object this$status = this.getStatus();
        final Object other$status = other.getStatus();
        if (this$status == null) {
            if (other$status == null) {
                return true;
            }
        }
        else if (this$status.equals(other$status)) {
            return true;
        }
        return false;
    }
    
    protected boolean canEqual(final Object other) {
        return other instanceof AuditDataModel;
    }
    
    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $messageId = this.getMessageId();
        result = result * 59 + (($messageId == null) ? 43 : $messageId.hashCode());
        final Object $transactionId = this.getTransactionId();
        result = result * 59 + (($transactionId == null) ? 43 : $transactionId.hashCode());
        final Object $action = this.getAction();
        result = result * 59 + (($action == null) ? 43 : $action.hashCode());
        final Object $domain = this.getDomain();
        result = result * 59 + (($domain == null) ? 43 : $domain.hashCode());
        final Object $coreVersion = this.getCoreVersion();
        result = result * 59 + (($coreVersion == null) ? 43 : $coreVersion.hashCode());
        final Object $createdOn = this.getCreatedOn();
        result = result * 59 + (($createdOn == null) ? 43 : $createdOn.hashCode());
        final Object $json = this.getJson();
        result = result * 59 + (($json == null) ? 43 : $json.hashCode());
        final Object $status = this.getStatus();
        result = result * 59 + (($status == null) ? 43 : $status.hashCode());
        return result;
    }
    
    @Override
    public String toString() {
        return "AuditDataModel(messageId=" + this.getMessageId() + ", transactionId=" + this.getTransactionId() + ", action=" + this.getAction() + ", domain=" + this.getDomain() + ", coreVersion=" + this.getCoreVersion() + ", createdOn=" + this.getCreatedOn() + ", json=" + this.getJson() + ", status=" + this.getStatus() + ")";
    }
}

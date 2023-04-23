// 
// Decompiled by Procyon v0.5.36
// 

package com.bb.beckn.common.model;

public class AuditModel
{
    private String subscriberId;
    private String apiName;
    private AuditDataModel dataModel;
    private HttpModel httpModel;
    private AuditFlagModel auditFlags;
    
    public AuditModel() {
        this.dataModel = new AuditDataModel();
        this.auditFlags = new AuditFlagModel();
    }
    
    public String getSubscriberId() {
        return this.subscriberId;
    }
    
    public String getApiName() {
        return this.apiName;
    }
    
    public AuditDataModel getDataModel() {
        return this.dataModel;
    }
    
    public HttpModel getHttpModel() {
        return this.httpModel;
    }
    
    public AuditFlagModel getAuditFlags() {
        return this.auditFlags;
    }
    
    public void setSubscriberId(final String subscriberId) {
        this.subscriberId = subscriberId;
    }
    
    public void setApiName(final String apiName) {
        this.apiName = apiName;
    }
    
    public void setDataModel(final AuditDataModel dataModel) {
        this.dataModel = dataModel;
    }
    
    public void setHttpModel(final HttpModel httpModel) {
        this.httpModel = httpModel;
    }
    
    public void setAuditFlags(final AuditFlagModel auditFlags) {
        this.auditFlags = auditFlags;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof AuditModel)) {
            return false;
        }
        final AuditModel other = (AuditModel)o;
        if (!other.canEqual(this)) {
            return false;
        }
        final Object this$subscriberId = this.getSubscriberId();
        final Object other$subscriberId = other.getSubscriberId();
        Label_0065: {
            if (this$subscriberId == null) {
                if (other$subscriberId == null) {
                    break Label_0065;
                }
            }
            else if (this$subscriberId.equals(other$subscriberId)) {
                break Label_0065;
            }
            return false;
        }
        final Object this$apiName = this.getApiName();
        final Object other$apiName = other.getApiName();
        Label_0102: {
            if (this$apiName == null) {
                if (other$apiName == null) {
                    break Label_0102;
                }
            }
            else if (this$apiName.equals(other$apiName)) {
                break Label_0102;
            }
            return false;
        }
        final Object this$dataModel = this.getDataModel();
        final Object other$dataModel = other.getDataModel();
        Label_0139: {
            if (this$dataModel == null) {
                if (other$dataModel == null) {
                    break Label_0139;
                }
            }
            else if (this$dataModel.equals(other$dataModel)) {
                break Label_0139;
            }
            return false;
        }
        final Object this$httpModel = this.getHttpModel();
        final Object other$httpModel = other.getHttpModel();
        Label_0176: {
            if (this$httpModel == null) {
                if (other$httpModel == null) {
                    break Label_0176;
                }
            }
            else if (this$httpModel.equals(other$httpModel)) {
                break Label_0176;
            }
            return false;
        }
        final Object this$auditFlags = this.getAuditFlags();
        final Object other$auditFlags = other.getAuditFlags();
        if (this$auditFlags == null) {
            if (other$auditFlags == null) {
                return true;
            }
        }
        else if (this$auditFlags.equals(other$auditFlags)) {
            return true;
        }
        return false;
    }
    
    protected boolean canEqual(final Object other) {
        return other instanceof AuditModel;
    }
    
    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $subscriberId = this.getSubscriberId();
        result = result * 59 + (($subscriberId == null) ? 43 : $subscriberId.hashCode());
        final Object $apiName = this.getApiName();
        result = result * 59 + (($apiName == null) ? 43 : $apiName.hashCode());
        final Object $dataModel = this.getDataModel();
        result = result * 59 + (($dataModel == null) ? 43 : $dataModel.hashCode());
        final Object $httpModel = this.getHttpModel();
        result = result * 59 + (($httpModel == null) ? 43 : $httpModel.hashCode());
        final Object $auditFlags = this.getAuditFlags();
        result = result * 59 + (($auditFlags == null) ? 43 : $auditFlags.hashCode());
        return result;
    }
    
    @Override
    public String toString() {
        return "AuditModel(subscriberId=" + this.getSubscriberId() + ", apiName=" + this.getApiName() + ", dataModel=" + this.getDataModel() + ", httpModel=" + this.getHttpModel() + ", auditFlags=" + this.getAuditFlags() + ")";
    }
}

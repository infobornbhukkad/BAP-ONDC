// 
// Decompiled by Procyon v0.5.36
// 

package com.bb.beckn.common.model;

import java.io.Serializable;

public class SigningModel implements Serializable
{
    private static final long serialVersionUID = -8316616688286772465L;
    private String privateKey;
    private String algo;
    private boolean certificateUsed;
    private String certificateType;
    private String certificateAlias;
    private String certificatePath;
    private String certificatePwd;
    
    public String getPrivateKey() {
        return this.privateKey;
    }
    
    public String getAlgo() {
        return this.algo;
    }
    
    public boolean isCertificateUsed() {
        return this.certificateUsed;
    }
    
    public String getCertificateType() {
        return this.certificateType;
    }
    
    public String getCertificateAlias() {
        return this.certificateAlias;
    }
    
    public String getCertificatePath() {
        return this.certificatePath;
    }
    
    public String getCertificatePwd() {
        return this.certificatePwd;
    }
    
    public void setPrivateKey(final String privateKey) {
        this.privateKey = privateKey;
    }
    
    public void setAlgo(final String algo) {
        this.algo = algo;
    }
    
    public void setCertificateUsed(final boolean certificateUsed) {
        this.certificateUsed = certificateUsed;
    }
    
    public void setCertificateType(final String certificateType) {
        this.certificateType = certificateType;
    }
    
    public void setCertificateAlias(final String certificateAlias) {
        this.certificateAlias = certificateAlias;
    }
    
    public void setCertificatePath(final String certificatePath) {
        this.certificatePath = certificatePath;
    }
    
    public void setCertificatePwd(final String certificatePwd) {
        this.certificatePwd = certificatePwd;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof SigningModel)) {
            return false;
        }
        final SigningModel other = (SigningModel)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.isCertificateUsed() != other.isCertificateUsed()) {
            return false;
        }
        final Object this$privateKey = this.getPrivateKey();
        final Object other$privateKey = other.getPrivateKey();
        Label_0078: {
            if (this$privateKey == null) {
                if (other$privateKey == null) {
                    break Label_0078;
                }
            }
            else if (this$privateKey.equals(other$privateKey)) {
                break Label_0078;
            }
            return false;
        }
        final Object this$algo = this.getAlgo();
        final Object other$algo = other.getAlgo();
        Label_0115: {
            if (this$algo == null) {
                if (other$algo == null) {
                    break Label_0115;
                }
            }
            else if (this$algo.equals(other$algo)) {
                break Label_0115;
            }
            return false;
        }
        final Object this$certificateType = this.getCertificateType();
        final Object other$certificateType = other.getCertificateType();
        Label_0152: {
            if (this$certificateType == null) {
                if (other$certificateType == null) {
                    break Label_0152;
                }
            }
            else if (this$certificateType.equals(other$certificateType)) {
                break Label_0152;
            }
            return false;
        }
        final Object this$certificateAlias = this.getCertificateAlias();
        final Object other$certificateAlias = other.getCertificateAlias();
        Label_0189: {
            if (this$certificateAlias == null) {
                if (other$certificateAlias == null) {
                    break Label_0189;
                }
            }
            else if (this$certificateAlias.equals(other$certificateAlias)) {
                break Label_0189;
            }
            return false;
        }
        final Object this$certificatePath = this.getCertificatePath();
        final Object other$certificatePath = other.getCertificatePath();
        Label_0226: {
            if (this$certificatePath == null) {
                if (other$certificatePath == null) {
                    break Label_0226;
                }
            }
            else if (this$certificatePath.equals(other$certificatePath)) {
                break Label_0226;
            }
            return false;
        }
        final Object this$certificatePwd = this.getCertificatePwd();
        final Object other$certificatePwd = other.getCertificatePwd();
        if (this$certificatePwd == null) {
            if (other$certificatePwd == null) {
                return true;
            }
        }
        else if (this$certificatePwd.equals(other$certificatePwd)) {
            return true;
        }
        return false;
    }
    
    protected boolean canEqual(final Object other) {
        return other instanceof SigningModel;
    }
    
    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * 59 + (this.isCertificateUsed() ? 79 : 97);
        final Object $privateKey = this.getPrivateKey();
        result = result * 59 + (($privateKey == null) ? 43 : $privateKey.hashCode());
        final Object $algo = this.getAlgo();
        result = result * 59 + (($algo == null) ? 43 : $algo.hashCode());
        final Object $certificateType = this.getCertificateType();
        result = result * 59 + (($certificateType == null) ? 43 : $certificateType.hashCode());
        final Object $certificateAlias = this.getCertificateAlias();
        result = result * 59 + (($certificateAlias == null) ? 43 : $certificateAlias.hashCode());
        final Object $certificatePath = this.getCertificatePath();
        result = result * 59 + (($certificatePath == null) ? 43 : $certificatePath.hashCode());
        final Object $certificatePwd = this.getCertificatePwd();
        result = result * 59 + (($certificatePwd == null) ? 43 : $certificatePwd.hashCode());
        return result;
    }
    
    @Override
    public String toString() {
        return "SigningModel(privateKey=" + this.getPrivateKey() + ", algo=" + this.getAlgo() + ", certificateUsed=" + this.isCertificateUsed() + ", certificateType=" + this.getCertificateType() + ", certificateAlias=" + this.getCertificateAlias() + ", certificatePath=" + this.getCertificatePath() + ", certificatePwd=" + this.getCertificatePwd() + ")";
    }
}

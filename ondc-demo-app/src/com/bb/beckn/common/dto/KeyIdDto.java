// 
// Decompiled by Procyon v0.5.36
// 

package com.bb.beckn.common.dto;

public class KeyIdDto
{
    private String keyId;
    private String uniqueKeyId;
    private String algo;
    
    public String getKeyId() {
        return this.keyId;
    }
    
    public String getUniqueKeyId() {
        return this.uniqueKeyId;
    }
    
    public String getAlgo() {
        return this.algo;
    }
    
    public void setKeyId(final String keyId) {
        this.keyId = keyId;
    }
    
    public void setUniqueKeyId(final String uniqueKeyId) {
        this.uniqueKeyId = uniqueKeyId;
    }
    
    public void setAlgo(final String algo) {
        this.algo = algo;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof KeyIdDto)) {
            return false;
        }
        final KeyIdDto other = (KeyIdDto)o;
        if (!other.canEqual(this)) {
            return false;
        }
        final Object this$keyId = this.getKeyId();
        final Object other$keyId = other.getKeyId();
        Label_0065: {
            if (this$keyId == null) {
                if (other$keyId == null) {
                    break Label_0065;
                }
            }
            else if (this$keyId.equals(other$keyId)) {
                break Label_0065;
            }
            return false;
        }
        final Object this$uniqueKeyId = this.getUniqueKeyId();
        final Object other$uniqueKeyId = other.getUniqueKeyId();
        Label_0102: {
            if (this$uniqueKeyId == null) {
                if (other$uniqueKeyId == null) {
                    break Label_0102;
                }
            }
            else if (this$uniqueKeyId.equals(other$uniqueKeyId)) {
                break Label_0102;
            }
            return false;
        }
        final Object this$algo = this.getAlgo();
        final Object other$algo = other.getAlgo();
        if (this$algo == null) {
            if (other$algo == null) {
                return true;
            }
        }
        else if (this$algo.equals(other$algo)) {
            return true;
        }
        return false;
    }
    
    protected boolean canEqual(final Object other) {
        return other instanceof KeyIdDto;
    }
    
    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $keyId = this.getKeyId();
        result = result * 59 + (($keyId == null) ? 43 : $keyId.hashCode());
        final Object $uniqueKeyId = this.getUniqueKeyId();
        result = result * 59 + (($uniqueKeyId == null) ? 43 : $uniqueKeyId.hashCode());
        final Object $algo = this.getAlgo();
        result = result * 59 + (($algo == null) ? 43 : $algo.hashCode());
        return result;
    }
    
    @Override
    public String toString() {
        return "KeyIdDto(keyId=" + this.getKeyId() + ", uniqueKeyId=" + this.getUniqueKeyId() + ", algo=" + this.getAlgo() + ")";
    }
}

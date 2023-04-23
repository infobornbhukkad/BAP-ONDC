// 
// Decompiled by Procyon v0.5.36
// 

package com.bb.beckn.common.model;

public class ProviderModel
{
    private String keyid;
    private String publickey;
    private String algo;
    
    public String getKeyid() {
        return this.keyid;
    }
    
    public String getPublickey() {
        return this.publickey;
    }
    
    public String getAlgo() {
        return this.algo;
    }
    
    public void setKeyid(final String keyid) {
        this.keyid = keyid;
    }
    
    public void setPublickey(final String publickey) {
        this.publickey = publickey;
    }
    
    public void setAlgo(final String algo) {
        this.algo = algo;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ProviderModel)) {
            return false;
        }
        final ProviderModel other = (ProviderModel)o;
        if (!other.canEqual(this)) {
            return false;
        }
        final Object this$keyid = this.getKeyid();
        final Object other$keyid = other.getKeyid();
        Label_0065: {
            if (this$keyid == null) {
                if (other$keyid == null) {
                    break Label_0065;
                }
            }
            else if (this$keyid.equals(other$keyid)) {
                break Label_0065;
            }
            return false;
        }
        final Object this$publickey = this.getPublickey();
        final Object other$publickey = other.getPublickey();
        Label_0102: {
            if (this$publickey == null) {
                if (other$publickey == null) {
                    break Label_0102;
                }
            }
            else if (this$publickey.equals(other$publickey)) {
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
        return other instanceof ProviderModel;
    }
    
    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $keyid = this.getKeyid();
        result = result * 59 + (($keyid == null) ? 43 : $keyid.hashCode());
        final Object $publickey = this.getPublickey();
        result = result * 59 + (($publickey == null) ? 43 : $publickey.hashCode());
        final Object $algo = this.getAlgo();
        result = result * 59 + (($algo == null) ? 43 : $algo.hashCode());
        return result;
    }
    
    @Override
    public String toString() {
        return "ProviderModel(keyid=" + this.getKeyid() + ", publickey=" + this.getPublickey() + ", algo=" + this.getAlgo() + ")";
    }
}

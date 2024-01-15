// 
// Decompiled by Procyon v0.5.36
// 

package com.bb.beckn.common.model;

public class HeaderParams
{
    private String created;
    private String expires;
    private String diagest;
    
    public String getCreated() {
        return this.created;
    }
    
    public String getExpires() {
        return this.expires;
    }
    
    public String getDiagest() {
        return this.diagest;
    }
    
    public void setCreated(final String created) {
        this.created = created;
    }
    
    public void setExpires(final String expires) {
        this.expires = expires;
    }
    
    public void setDiagest(final String diagest) {
        this.diagest = diagest;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof HeaderParams)) {
            return false;
        }
        final HeaderParams other = (HeaderParams)o;
        if (!other.canEqual(this)) {
            return false;
        }
        final Object this$created = this.getCreated();
        final Object other$created = other.getCreated();
        Label_0065: {
            if (this$created == null) {
                if (other$created == null) {
                    break Label_0065;
                }
            }
            else if (this$created.equals(other$created)) {
                break Label_0065;
            }
            return false;
        }
        final Object this$expires = this.getExpires();
        final Object other$expires = other.getExpires();
        Label_0102: {
            if (this$expires == null) {
                if (other$expires == null) {
                    break Label_0102;
                }
            }
            else if (this$expires.equals(other$expires)) {
                break Label_0102;
            }
            return false;
        }
        final Object this$diagest = this.getDiagest();
        final Object other$diagest = other.getDiagest();
        if (this$diagest == null) {
            if (other$diagest == null) {
                return true;
            }
        }
        else if (this$diagest.equals(other$diagest)) {
            return true;
        }
        return false;
    }
    
    protected boolean canEqual(final Object other) {
        return other instanceof HeaderParams;
    }
    
    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $created = this.getCreated();
        result = result * 59 + (($created == null) ? 43 : $created.hashCode());
        final Object $expires = this.getExpires();
        result = result * 59 + (($expires == null) ? 43 : $expires.hashCode());
        final Object $diagest = this.getDiagest();
        result = result * 59 + (($diagest == null) ? 43 : $diagest.hashCode());
        return result;
    }
    
    @Override
    public String toString() {
        return "HeaderParams(created=" + this.getCreated() + ", expires=" + this.getExpires() + ", diagest=" + this.getDiagest() + ")";
    }
}

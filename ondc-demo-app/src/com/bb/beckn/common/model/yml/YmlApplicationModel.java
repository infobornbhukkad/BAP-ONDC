// 
// Decompiled by Procyon v0.5.36
// 

package com.bb.beckn.common.model.yml;

public class YmlApplicationModel
{
    private YmlBeckn beckn;
    
    public YmlBeckn getBeckn() {
        return this.beckn;
    }
    
    public void setBeckn(final YmlBeckn beckn) {
        this.beckn = beckn;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof YmlApplicationModel)) {
            return false;
        }
        final YmlApplicationModel other = (YmlApplicationModel)o;
        if (!other.canEqual(this)) {
            return false;
        }
        final Object this$beckn = this.getBeckn();
        final Object other$beckn = other.getBeckn();
        if (this$beckn == null) {
            if (other$beckn == null) {
                return true;
            }
        }
        else if (this$beckn.equals(other$beckn)) {
            return true;
        }
        return false;
    }
    
    protected boolean canEqual(final Object other) {
        return other instanceof YmlApplicationModel;
    }
    
    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $beckn = this.getBeckn();
        result = result * 59 + (($beckn == null) ? 43 : $beckn.hashCode());
        return result;
    }
    
    @Override
    public String toString() {
        return "YmlApplicationModel(beckn=" + this.getBeckn() + ")";
    }
}

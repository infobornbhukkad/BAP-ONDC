// 
// Decompiled by Procyon v0.5.36
// 

package com.bb.beckn.common.model.yml;

public class YmlBeckn
{
    private YmlPersistence persistence;
    
    public YmlPersistence getPersistence() {
        return this.persistence;
    }
    
    public void setPersistence(final YmlPersistence persistence) {
        this.persistence = persistence;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof YmlBeckn)) {
            return false;
        }
        final YmlBeckn other = (YmlBeckn)o;
        if (!other.canEqual(this)) {
            return false;
        }
        final Object this$persistence = this.getPersistence();
        final Object other$persistence = other.getPersistence();
        if (this$persistence == null) {
            if (other$persistence == null) {
                return true;
            }
        }
        else if (this$persistence.equals(other$persistence)) {
            return true;
        }
        return false;
    }
    
    protected boolean canEqual(final Object other) {
        return other instanceof YmlBeckn;
    }
    
    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $persistence = this.getPersistence();
        result = result * 59 + (($persistence == null) ? 43 : $persistence.hashCode());
        return result;
    }
    
    @Override
    public String toString() {
        return "YmlBeckn(persistence=" + this.getPersistence() + ")";
    }
}

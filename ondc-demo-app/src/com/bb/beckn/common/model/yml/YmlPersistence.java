// 
// Decompiled by Procyon v0.5.36
// 

package com.bb.beckn.common.model.yml;

public class YmlPersistence
{
    private String type;
    
    public String getType() {
        return this.type;
    }
    
    public void setType(final String type) {
        this.type = type;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof YmlPersistence)) {
            return false;
        }
        final YmlPersistence other = (YmlPersistence)o;
        if (!other.canEqual(this)) {
            return false;
        }
        final Object this$type = this.getType();
        final Object other$type = other.getType();
        if (this$type == null) {
            if (other$type == null) {
                return true;
            }
        }
        else if (this$type.equals(other$type)) {
            return true;
        }
        return false;
    }
    
    protected boolean canEqual(final Object other) {
        return other instanceof YmlPersistence;
    }
    
    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $type = this.getType();
        result = result * 59 + (($type == null) ? 43 : $type.hashCode());
        return result;
    }
    
    @Override
    public String toString() {
        return "YmlPersistence(type=" + this.getType() + ")";
    }
}

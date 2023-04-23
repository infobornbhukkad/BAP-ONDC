// 
// Decompiled by Procyon v0.5.36
// 

package com.bb.beckn.common.enums;

public enum OndcUserType
{
    BUYER("buyer"), 
    SELLER("seller");
    
    String type;
    
    private OndcUserType(final String type) {
        this.type = type;
    }
    
    public String type() {
        return this.type;
    }
    
    private static /* synthetic */ OndcUserType[] $values() {
        return new OndcUserType[] { OndcUserType.BUYER, OndcUserType.SELLER };
    }
    
}

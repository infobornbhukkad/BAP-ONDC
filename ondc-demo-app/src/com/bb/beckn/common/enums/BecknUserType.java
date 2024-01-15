// 
// Decompiled by Procyon v0.5.36
// 

package com.bb.beckn.common.enums;

public enum BecknUserType
{
    BAP("BAP"), 
    BPP("BPP");
    
    String type;
    
    private BecknUserType(final String type) {
        this.type = type;
    }
    
    public String type() {
        return this.type;
    }
    
    private static /* synthetic */ BecknUserType[] $values() {
        return new BecknUserType[] { BecknUserType.BAP, BecknUserType.BPP };
    }
}

// 
// Decompiled by Procyon v0.5.36
// 

package com.bb.beckn.common.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AdaptorUtil
{
    @Value("${beckn.persistence.type}")
    private String persistenceTypes;
    
    public boolean isDataBasePersistanceConfigured() {
        final String[] types = this.persistenceTypes.split("\\|");
        if (types != null && types.length > 0) {
            for (final String type : types) {
                if ("db-postgres".equals(type.trim())) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean isFilePersistanceConfigured() {
        final String[] types = this.persistenceTypes.split("\\|");
        if (types != null && types.length > 0) {
            for (final String type : types) {
                if ("file".equals(type.trim())) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean isHttpPersistanceConfigured() {
        final String[] types = this.persistenceTypes.split("\\|");
        if (types != null && types.length > 0) {
            for (final String type : types) {
                if ("http".equals(type.trim())) {
                    return true;
                }
            }
        }
        return false;
    }
}

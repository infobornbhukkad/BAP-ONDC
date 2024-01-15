// 
// Decompiled by Procyon v0.5.36
// 

package com.bb.beckn.common.constant;

import java.util.Arrays;
import java.util.List;

public final class ApplicationConstant
{
    public static final String PIPE = "|";
    public static final String BECKN_API_COMMON_CACHE = "beckn-api-cache-common";
    public static final String BECKN_API_LOOKUP_CACHE = "beckn-api-cache-lookup";
    public static final String CONTENT_TYPE_JSON = "application/json";
    public static final String SIGN_ALGORITHM = "ed25519";
    public static final String DB_POSTGRES = "db-postgres";
    public static final String FILE = "file";
    public static final String HTTP = "http";
    public static final List<String> ALLOWED_PERSISTENCE_TYPES;
    
    private ApplicationConstant() {
    }
    
    static {
        ALLOWED_PERSISTENCE_TYPES = Arrays.asList("http", "db-postgres");
    }
}

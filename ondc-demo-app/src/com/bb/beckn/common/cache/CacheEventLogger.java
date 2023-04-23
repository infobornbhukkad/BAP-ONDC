// 
// Decompiled by Procyon v0.5.36
// 

package com.bb.beckn.common.cache;

import org.slf4j.LoggerFactory;
import org.ehcache.event.CacheEvent;
import org.slf4j.Logger;
import org.ehcache.event.CacheEventListener;

public class CacheEventLogger implements CacheEventListener<Object, Object>
{
    private static final Logger log;
    
    public void onEvent(final CacheEvent<?, ?> event) {
        //CacheEventLogger.log.info("EventType: {} | Key: {} | Old Value: {} | New Value {}", new Object[] { event.getType(), event.getKey(), event.getOldValue(), event.getNewValue() });
    }
    
    static {
        log = LoggerFactory.getLogger((Class)CacheEventLogger.class);
    }
}

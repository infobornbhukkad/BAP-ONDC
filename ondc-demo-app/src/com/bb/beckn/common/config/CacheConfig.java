// 
// Decompiled by Procyon v0.5.36
// 

package com.bb.beckn.common.config;

import org.slf4j.LoggerFactory;
import org.ehcache.event.CacheEvent;
import java.lang.reflect.Method;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.cache.interceptor.KeyGenerator;
import java.util.Set;
import org.ehcache.event.CacheEventListener;
import java.util.EnumSet;
import com.bb.beckn.common.cache.CacheEventLogger;
import org.ehcache.config.builders.CacheEventListenerConfigurationBuilder;
import org.ehcache.event.EventType;
import org.ehcache.impl.config.event.DefaultCacheEventListenerConfiguration;
import org.springframework.context.annotation.Bean;
import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import java.time.Duration;
import org.ehcache.spi.service.ServiceConfiguration;
import org.ehcache.config.Builder;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.units.MemoryUnit;
import org.ehcache.config.ResourceUnit;
import org.ehcache.config.units.EntryUnit;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.slf4j.Logger;
import org.springframework.cache.annotation.CachingConfigurerSupport;

public class CacheConfig extends CachingConfigurerSupport
{
    private static final Logger log;
    private static final String CACHE_PATH = "ehcache.cacheregion.beckn-api.";
    @Autowired
    private Environment env;
    
    @Bean
    public CacheManager ehCacheManager() {
        CacheConfig.log.info("[Ehcache configuration initialization <start>]");
        final int entryCount = (int)this.env.getProperty("ehcache.cacheregion.beckn-api.common-cache.entrycount", (Class)Integer.class);
        final CacheConfiguration<String, String> cacheConfiguration = (CacheConfiguration<String, String>)CacheConfigurationBuilder.newCacheConfigurationBuilder((Class)String.class, (Class)String.class, (Builder)ResourcePoolsBuilder.newResourcePoolsBuilder().heap((long)entryCount, (ResourceUnit)EntryUnit.ENTRIES).offheap(100L, MemoryUnit.MB)).withService((ServiceConfiguration)this.getEventListenerConfiguation()).withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofMinutes((int)this.env.getProperty("ehcache.cacheregion.beckn-api.common-cache.timetolive", (Class)Integer.class)))).build();
        final CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder().withCache("beckn-api-cache-common", (CacheConfiguration)cacheConfiguration).build(true);
        CacheConfig.log.info("[Ehcache configuration initialization <complete>]");
        return cacheManager;
    }
    
    private DefaultCacheEventListenerConfiguration getEventListenerConfiguation() {
        return CacheEventListenerConfigurationBuilder.newEventListenerConfiguration(cacheEvent -> CacheConfig.log.warn("Policy " + cacheEvent.getOldValue() + " has been evicted. Check your max heap size settings"), EventType.CREATED, new EventType[] { EventType.UPDATED, EventType.EXPIRED, EventType.EVICTED, EventType.REMOVED }).build();
    }
    
    private CacheEventListenerConfigurationBuilder getEventListenerConfiguation1() {
        return CacheEventListenerConfigurationBuilder.newEventListenerConfiguration((CacheEventListener)new CacheEventLogger(), (Set)EnumSet.of(EventType.CREATED, EventType.UPDATED, EventType.EXPIRED, EventType.EVICTED, EventType.REMOVED)).unordered().asynchronous();
    }
    
    public KeyGenerator keyGenerator() {
        return (KeyGenerator)new SimpleKeyGenerator() {
            public Object generate(final Object target, final Method method, final Object... params) {
                return target.getClass().getName() + "|" + method.getName() + "|" + generateKey(params);
            }
        };
    }
    
    static {
        log = LoggerFactory.getLogger((Class)CacheConfig.class);
    }
}

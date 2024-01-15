// 
// Decompiled by Procyon v0.5.36
// 

package com.bb.beckn.common.cache;

import org.slf4j.LoggerFactory;
import java.util.Set;
import org.ehcache.event.CacheEventListener;
import java.util.EnumSet;
import org.ehcache.event.EventType;
import org.ehcache.expiry.Expirations;
import org.ehcache.config.builders.CacheEventListenerConfigurationBuilder;
import org.springframework.cache.interceptor.KeyGenerator;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import java.time.Duration;
import org.ehcache.config.units.MemoryUnit;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.jsr107.Eh107Configuration;
import org.ehcache.config.Builder;
import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.EvictionAdvisor;
import org.springframework.context.annotation.Bean;
import javax.cache.spi.CachingProvider;
import javax.cache.Caching;
import javax.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.slf4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.cache.annotation.EnableCaching;
import org.ehcache.Cache;

@EnableCaching
@Configuration
public class ApplicationCacheConfig
{
    private static final Logger log;
    private static final String CACHE_PATH_COMMON = "ehcache.cacheregion.beckn-api.common-cache.";
    private static final String CACHE_PATH_LOOKUP = "ehcache.cacheregion.beckn-api.lookup-cache.";
    private static final String CACHE_PATH_OWN = "ehcache.cacheregion.beckn-api.own-cache.";
    @Autowired
    private Environment env;
    public javax.cache.Cache<String, String> cache;
    @Bean
    public CacheManager ehCacheManager() {
        final CachingProvider provider = Caching.getCachingProvider();
        final CacheManager cacheManager = provider.getCacheManager();
        this.createCommonCache(cacheManager);
        this.createLookupCache(cacheManager);
        //this.createOwnCache(cacheManager);
        return cacheManager;
    }
    
   /* private void createOwnCache(final CacheManager cacheManager) {
        final int entryCount = (int)this.env.getProperty(CACHE_PATH_OWN + "entrycount", (Class)Integer.class);
        final int timetolive = (int)this.env.getProperty(CACHE_PATH_OWN + "timetolive", (Class)Integer.class);
        ApplicationCacheConfig.log.info("own-cache.entrycount: {} & own-cache.timetolive {}", (Object)entryCount, (Object)timetolive);
        final CacheConfigurationBuilder<Object, Object> configurationBuilder = this.buildConfiguration(entryCount, timetolive);
        cacheManager.createCache("beckn-api-own-cache", Eh107Configuration.fromEhcacheCacheConfiguration((Builder)configurationBuilder.withService((Builder)this.getAsynchronousListener())));
    
    }*/
    
    private void createCommonCache(final CacheManager cacheManager) {
        final int entryCount = (int)this.env.getProperty("ehcache.cacheregion.beckn-api.common-cache.entrycount", (Class)Integer.class);
        final int timetolive = (int)this.env.getProperty("ehcache.cacheregion.beckn-api.common-cache.timetolive", (Class)Integer.class);
        ApplicationCacheConfig.log.info("common-cache.entrycount: {} & common-cache.timetolive {}", (Object)entryCount, (Object)timetolive);
        final CacheConfigurationBuilder<Object, Object> configurationBuilder = this.buildConfiguration(entryCount, timetolive);
        cacheManager.createCache("beckn-api-cache-common", Eh107Configuration.fromEhcacheCacheConfiguration((Builder)configurationBuilder.withService((Builder)this.getAsynchronousListener())));
    }
    
    private void createLookupCache(final CacheManager cacheManager) {
        final int entryCount = (int)this.env.getProperty("ehcache.cacheregion.beckn-api.lookup-cache.entrycount", (Class)Integer.class);
        final int timetolive = (int)this.env.getProperty("ehcache.cacheregion.beckn-api.lookup-cache.timetolive", (Class)Integer.class);
        ApplicationCacheConfig.log.info("lookup-cache.entrycount: {} & lookup-cache.timetolive {}", (Object)entryCount, (Object)timetolive);
        final CacheConfigurationBuilder<Object, Object> configurationBuilder = this.buildConfiguration(entryCount, timetolive);
        cacheManager.createCache("beckn-api-cache-lookup", Eh107Configuration.fromEhcacheCacheConfiguration((Builder)configurationBuilder.withService((Builder)this.getAsynchronousListener())));
    }
    
    private CacheConfigurationBuilder<Object, Object> buildConfiguration(final int entryCount, final int timetolive) {
        return (CacheConfigurationBuilder<Object, Object>)CacheConfigurationBuilder.newCacheConfigurationBuilder((Class)Object.class, (Class)Object.class, (Builder)ResourcePoolsBuilder.heap((long)entryCount).offheap(25L, MemoryUnit.MB)).withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(timetolive)));
    }
    
    @Bean({ "customKeyGenerator" })
    public KeyGenerator keyGenerator() {
        return (KeyGenerator)new CustomKeyGenerator();
    }
    
    private CacheEventListenerConfigurationBuilder getAsynchronousListener() {
        return CacheEventListenerConfigurationBuilder.newEventListenerConfiguration((CacheEventListener)new CacheEventLogger(), (Set)EnumSet.of(EventType.CREATED, EventType.UPDATED, EventType.EXPIRED, EventType.EVICTED, EventType.REMOVED)).unordered().asynchronous();
    }
    
    static {
        log = LoggerFactory.getLogger((Class)ApplicationCacheConfig.class);
    }
}

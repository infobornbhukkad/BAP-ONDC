// 
// Decompiled by Procyon v0.5.36
// 

package com.bb.beckn.common.cache;

import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class CachingService
{
    private static final Logger log;
    @Autowired
    private CacheManager cacheManager;
    
    public void putToCache(final String cacheName, final String key, final Object value) {
        //CachingService.log.info("putting in cache {} with key {}", (Object)cacheName, (Object)key);
        this.cacheManager.getCache(cacheName).put((Object)key, value);
    }
    
    public Object getFromCache(final String cacheName, final String key) {
        final Cache.ValueWrapper wrapper = this.cacheManager.getCache(cacheName).get((Object)key);
        if (wrapper != null) {
            final Object object = wrapper.get();
            //CachingService.log.info("The value of getFromCache is {}", object);
            return object;
        }
        return null;
    }
    
    public String getFromCache1(final String cacheName, final String key) {
        String value = null;
        if (this.cacheManager.getCache(cacheName).get((Object)key) != null) {
            value = this.cacheManager.getCache(cacheName).get((Object)key).get().toString();
        }
        return value;
    }
    
    @CacheEvict(value = { "first" }, key = "#cacheKey")
    public void evictSingleCacheValue(final String cacheKey) {
    }
    
    @CacheEvict(value = { "first" }, allEntries = true)
    public void evictAllCacheValues() {
    }
    
    public void evictSingleCacheValue(final String cacheName, final String cacheKey) {
        this.cacheManager.getCache(cacheName).evict((Object)cacheKey);
    }
    
    public void evictAllCacheValues(final String cacheName) {
        this.cacheManager.getCache(cacheName).clear();
    }
    
    public void evictAllCaches() {
        this.cacheManager.getCacheNames().parallelStream().forEach(cacheName -> this.cacheManager.getCache(cacheName).clear());
    }
    
    @Scheduled(fixedRate = 600000L)
    public void evictAllcachesAtIntervals() {
        this.evictAllCaches();
    }
    
    static {
        log = LoggerFactory.getLogger((Class)CachingService.class);
    }
}

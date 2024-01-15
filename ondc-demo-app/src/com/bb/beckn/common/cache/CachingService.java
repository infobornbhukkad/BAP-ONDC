package com.bb.beckn.common.cache;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.bb.beckn.api.model.lookup.LookupResponse;
import com.bb.beckn.common.service.LookupService;

@Component
public class CachingService {
	private static final Logger log = LoggerFactory.getLogger(CachingService.class);

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
	            CachingService.log.info("The value of getFromCache is {}", object);
	            return object;
	        }
	        return null;
	    }

	public String getFromCache1(String cacheName, String key) {
		String value = null;
		
		if (this.cacheManager.getCache(cacheName).get(key) != null) {
			value = this.cacheManager.getCache(cacheName).get(key).get().toString();
		}
		return value;
	}

	@CacheEvict(value = { "first" }, key = "#cacheKey")
	public void evictSingleCacheValue(String cacheKey) {
		log.info("received request to evict single cache value...............");
	}

	@CacheEvict(value = { "first" }, allEntries = true)
	public void evictAllCacheValues() {
		log.info("evictAllCacheValues..................");
	}

	public void evictSingleCacheValue(String cacheName, String cacheKey) {
		this.cacheManager.getCache(cacheName).evict(cacheKey);
	}

	public boolean evictAllCacheValues(String cacheName) {
		log.info("received request to evict cache region {} ...........", cacheName);
		Cache cache = this.cacheManager.getCache(cacheName);
		if (cache != null) {
			cache.clear();
			log.info("cache region {} cleaned", cacheName);
			return true;
		}
		log.warn("cache region {} not cleaned", cacheName);
		return false;
	}

	public Collection<String> evictAllCacheRegions() {
		log.info("received request to evict all cache region..................");
		Collection<String> cacheNames = this.cacheManager.getCacheNames();

		for (String cacheName : cacheNames) {
			Cache cache = this.cacheManager.getCache(cacheName);
			if (cache != null) {
				cache.clear();
				log.info("cache region {} cleaned", cacheName);
			}
		}

		log.warn("cache cleared for regions {}", cacheNames);
		return cacheNames;
	}

	public void evictAllCaches() {
		log.info("inside evictAllCaches.CachingService class ......................");
		/*this.cacheManager.getCacheNames().parallelStream()
				.forEach(cacheName -> this.cacheManager.getCache(cacheName).clear());*/
		for (String cacheName : cacheManager.getCacheNames()) {
			
			if(cacheName.equalsIgnoreCase("beckn-api-own-cache")) {
				log.info("cacheName iffff.................." +cacheName );
				continue;
			}else {
				log.info("cacheName clearing.................." +cacheName );
		       cacheManager.getCache(cacheName).clear();
			}
		}
		
	}

	@Scheduled(fixedRate = 600000L)
	public void evictAllcachesAtIntervals() {
		log.info("inside evictAllcachesAtIntervals CachingService class.......................");
		evictAllCaches();
	}
	
	
}

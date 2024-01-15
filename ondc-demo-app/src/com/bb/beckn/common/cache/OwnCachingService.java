package com.bb.beckn.common.cache;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import com.bb.beckn.api.model.lookup.LookupResponse;
import com.bb.beckn.common.service.LookupService;

@EnableScheduling
@Component
public class OwnCachingService {
	private static final Logger log = LoggerFactory.getLogger(OwnCachingService .class);

	@Autowired
	private CacheManager cacheManager;
	private long creationTime;
	private final HashMap<String, Instant> creationTimes = new LinkedHashMap<>();
    //private final AtomicInteger currentSize = new AtomicInteger();
    private final long maxAgeInSeconds = 40;
    int maxSize=0;
    @Autowired
    private Environment env;
    
    private static final String CACHE_PATH_OWN = "ehcache.cacheregion.beckn-api.own-cache.";
    
   // @Scheduled(fixedRate = 30000)
	public void evictAllcachesAtIntervals() {    	
    	log.info(" inside schedule method........");
    	maxSize = (int)this.env.getProperty(CACHE_PATH_OWN + "entrycount", (Class)Integer.class);
    	
    	if(creationTimes.size() != 0) {
    		log.info(" currentSize is not null   .......  " + maxSize + "creationTimes.size() --"+ creationTimes.size());
	    	//if (creationTimes.size() >= maxSize) {
	    		Instant now = Instant.now();
				//String strkey = creationTimes.keySet().iterator().next();
	    		Iterator<Entry<String, Instant> > new_Iterator  = creationTimes.entrySet().iterator();
				//for (String key : creationTimes.keySet()) {
				while (new_Iterator.hasNext()) {
					String key=new_Iterator.next().getKey();
			        System.out.println(creationTimes + ":" + key);
			    
					log.info(" strkey ------"+ key + "creationTimes.size()- "+creationTimes.size());
					if(creationTimes.get(key).isBefore(now.minusSeconds(maxAgeInSeconds)) || creationTimes.size() > maxSize) {
						log.info(" calling  evictSingleCacheValue method------------------");
						evictSingleCacheValue("beckn-api-own-cache", key);
						new_Iterator.remove();
					}
				
				}
				for(int i=0; i < creationTimes.size(); i++ ) {
					log.info(" creationTimes keys after--" + creationTimes.keySet());
				}
			//}
    	}
	}
    public void evictSingleCacheValue(String cacheName, String cacheKey) {
    	log.info("Called from Schedule", cacheName, cacheKey);
    	this.cacheManager.getCache(cacheName).evict(cacheKey);
	}
    
	public void putToCache(String cacheName, String key, Object value) {
		log.info("putting in cache {} with key {}", cacheName, key);
		maxSize = (int)this.env.getProperty(CACHE_PATH_OWN + "entrycount", (Class)Integer.class);
		Instant now = Instant.now();		
		creationTimes.put(key, now);
		 
		if (creationTimes.size() >= maxSize) {
			log.info("inside putToCache method and size has exceeded ++++++++++++++ " +creationTimes.size());
		    evictSingleCacheValue(cacheName);
		}
		log.info("Before putting value ", cacheName, key , value);
		this.cacheManager.getCache(cacheName).put(key, value);
	}

	public Object getFromCache(String cacheName, String key) {
		Cache.ValueWrapper wrapper = this.cacheManager.getCache(cacheName).get(key);
		if (wrapper != null) {
			Object object = wrapper.get();
			log.info("The value of getFromCache is {}", object);
			
			return object;
		}
		return null;
	}
	
	@CacheEvict(value = { "first" }, key = "#cacheKey")
	public void evictSingleCacheValue(String cacheKey) {
		log.info("inside evictSingleCacheValue method  ." + cacheKey);
		String strk= creationTimes.keySet().stream().findFirst().get();
		Optional<Entry<String, Instant>> ff =creationTimes.entrySet().stream().findFirst();
		@SuppressWarnings("unchecked")
		HashMap<String, Instant> gg	=(HashMap<String, Instant>) ff.map(Collections::singletonList).orElse(Collections.emptyList());
		System.out.println("Optional<Entry<String, Instant>> ---  "+ gg);
		log.info("Before size......" + creationTimes.size());
		creationTimes.remove(gg);
		log.info("After size......" + creationTimes.size());
		log.info("received request to evict single cache value..............." + creationTimes.keySet());
	}

	@CacheEvict(value = { "first" }, allEntries = true)
	public void evictAllCacheValues() {
		log.info("evictAllCacheValues..................");
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

	/*public void evictAllCaches() {
		log.info("inside evictAllCaches..OwnCachingService class.....................");
		this.cacheManager.getCacheNames().parallelStream()
				.forEach(cacheName -> this.cacheManager.getCache(cacheName).clear());
	}*/

	
	
	
}

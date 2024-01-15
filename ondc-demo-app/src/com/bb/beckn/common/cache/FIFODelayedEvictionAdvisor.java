package com.bb.beckn.common.cache;

import org.ehcache.config.EvictionAdvisor;
import org.ehcache.spi.loaderwriter.CacheLoaderWriter;

import java.time.Duration;
import java.time.Instant;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.cache.integration.CacheLoader;
import javax.cache.integration.CacheLoaderException;

public class FIFODelayedEvictionAdvisor<K, V> implements CacheLoader<K, V> {
    private final Map<K, Instant> creationTimes = new HashMap<>();
    private final Deque<K> evictionQueue = new LinkedList<>();
    private final int maxSize;
    private final Duration maxAge;

    public FIFODelayedEvictionAdvisor(int maxSize, Duration maxAge) {
        this.maxSize = maxSize;
        this.maxAge = maxAge;
    }

    public boolean adviseAgainstEviction(K key, V value) {
        Instant now = Instant.now();

        // Remove the oldest entry if the cache exceeds the maximum size
        if (creationTimes.size() >= maxSize) {
            K oldestKey = evictionQueue.removeFirst();
            creationTimes.remove(oldestKey);
        }

        // Add the new entry to the eviction queue and update its creation time
        evictionQueue.addLast(key);
        creationTimes.put(key, now);

        // Check if the entry's creation time exceeds the maximum age
        Instant creationTime = creationTimes.get(key);
        return creationTime != null && creationTime.plus(maxAge).isBefore(now);
    }

	public void delete(K arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public V load(K arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public void write(K arg0, V arg1) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<K, V> loadAll(Iterable<? extends K> keys) throws CacheLoaderException {
		// TODO Auto-generated method stub
		return null;
	}
}

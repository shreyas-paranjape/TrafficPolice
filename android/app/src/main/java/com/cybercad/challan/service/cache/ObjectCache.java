package com.cybercad.challan.service.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ObjectCache {

    private static final Cache<String, Object> cache = CacheBuilder.newBuilder()
            .initialCapacity(10)
            .maximumSize(1000)
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .build();

    public static void put(Map<String, Object> payload) {
        cache.putAll(payload);
    }

    public static Object get(String key) {
        return cache.getIfPresent(key);
    }

    public static void clear() {
        cache.invalidateAll();
        cache.cleanUp();
    }

}

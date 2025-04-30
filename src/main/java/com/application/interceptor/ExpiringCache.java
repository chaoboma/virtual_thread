package com.application.interceptor;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ExpiringCache<K, V> {
    private final Map<K, CacheValue<V>> cache = new ConcurrentHashMap<>();
    private final long timeout; // 毫秒

    public ExpiringCache(long timeout) {
        this.timeout = timeout;
    }

    public void put(K key, V value) {
        long expiryTime = System.currentTimeMillis() + timeout;
        cache.put(key, new CacheValue<>(value, expiryTime));
    }

    public V get(K key) {
        CacheValue<V> cacheValue = cache.get(key);
        if (cacheValue == null || System.currentTimeMillis() > cacheValue.expiryTime) {
            cache.remove(key); // 移除已过期的条目
            return null; // 缓存失效
        }
        return cacheValue.value;
    }

    private static class CacheValue<V> {
        V value;
        long expiryTime;

        CacheValue(V value, long expiryTime) {
            this.value = value;
            this.expiryTime = expiryTime;
        }
    }

    public static void main(String[] args) {
        ExpiringCache<String, String> cache = new ExpiringCache<>(5000); // 超时时间为5秒

        cache.put("key1", "value1");
        System.out.println("Stored key1: " + cache.get("key1")); // 输出: value1
        try {
            Thread.sleep(2000); // 等待6秒，让key1过期
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("After 2 seconds: " + cache.get("key1")); // 输出: null
        try {
            Thread.sleep(6000); // 等待6秒，让key1过期
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("After 6 seconds: " + cache.get("key1")); // 输出: null
    }
}

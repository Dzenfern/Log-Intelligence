package com.dylan.logintelligence.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisCounterService {

    private static final String COUNTER_KEY_PREFIX = "counter:";
    private static final long DEFAULT_EXPIRY_MINUTES = 5;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * Increment the counter for a specific category
     * @param category The category name
     * @return The new count after increment
     */
    public Long incrementCategory(String category) {
        String key = COUNTER_KEY_PREFIX + category;
        Long count = redisTemplate.opsForValue().increment(key);

        // Set expiry if this is a new key (count == 1)
        if (count != null && count == 1) {
            expireIfNew(category);
        }

        return count;
    }

    /**
     * Get the current count for a category
     * @param category The category name
     * @return The current count, or 0 if category doesn't exist
     */
    public Long getCount(String category) {
        String key = COUNTER_KEY_PREFIX + category;
        Object value = redisTemplate.opsForValue().get(key);

        if (value == null) {
            return 0L;
        }

        // Handle different possible return types
        if (value instanceof Integer) {
            return ((Integer) value).longValue();
        } else if (value instanceof Long) {
            return (Long) value;
        } else if (value instanceof String) {
            return Long.parseLong((String) value);
        }

        return 0L;
    }

    /**
     * Set expiry on a category counter if it's new
     * @param category The category name
     */
    public void expireIfNew(String category) {
        String key = COUNTER_KEY_PREFIX + category;
        // Check if key exists before setting expiry
        Boolean exists = redisTemplate.hasKey(key);
        if (Boolean.TRUE.equals(exists)) {
            // Set expiry only if key exists
            redisTemplate.expire(key, DEFAULT_EXPIRY_MINUTES, TimeUnit.MINUTES);
        }
    }




}
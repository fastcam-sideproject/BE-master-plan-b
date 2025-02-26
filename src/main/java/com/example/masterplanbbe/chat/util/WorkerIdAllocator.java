package com.example.masterplanbbe.chat.util;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class WorkerIdAllocator {
    private final StringRedisTemplate redisTemplate;

    public WorkerIdAllocator(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public long getWorkerId() {
        if(redisTemplate == null) {
            throw new IllegalStateException();
        }
        return redisTemplate.opsForValue().increment("workerId");
    }
}
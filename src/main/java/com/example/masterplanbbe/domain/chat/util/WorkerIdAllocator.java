package com.example.masterplanbbe.domain.chat.util;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WorkerIdAllocator {
    private final StringRedisTemplate redisTemplate;

    public long getWorkerId() {
        if(redisTemplate == null) {
            throw new IllegalStateException();
        }
        return redisTemplate.opsForValue().increment("workerId");
    }
}
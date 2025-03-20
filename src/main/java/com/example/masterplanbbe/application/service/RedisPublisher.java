package com.example.masterplanbbe.application.service;

import com.example.masterplanbbe.application.dto.ChatRedisDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RedisPublisher {
    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public RedisPublisher(@Qualifier("chatPubSubTemplate") RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 채팅 보내기
     * @param channel
     * @param chatRedisDto
     */
    public void publish(String channel, ChatRedisDto chatRedisDto) {
        try {
            redisTemplate.convertAndSend(channel, chatRedisDto);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}

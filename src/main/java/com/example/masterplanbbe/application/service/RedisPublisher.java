package com.example.masterplanbbe.application.service;

import com.example.masterplanbbe.application.dto.ChatRedisDto;
import com.example.masterplanbbe.infrastructure.exception.chat.ChatRedisPublishException;
import com.example.masterplanbbe.infrastructure.exception.chat.InvalidChatRedisKeyException;
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
        if (channel == null || chatRedisDto == null) {
            throw new InvalidChatRedisKeyException();  // 키가 null인 경우 예외 처리
        }
        try {
            redisTemplate.convertAndSend(channel, chatRedisDto);
        } catch (Exception e) {
            log.error("Redis publish 실패: {}", e.getMessage(), e);
            throw new ChatRedisPublishException(); // publish 실패 시 커스텀 예외 던짐
        }
    }
}

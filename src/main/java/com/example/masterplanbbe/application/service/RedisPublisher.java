package com.example.masterplanbbe.application.service;

import com.example.masterplanbbe.application.dto.ChatMessageDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RedisPublisher {
    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public RedisPublisher(@Qualifier("chatTemplate") RedisTemplate<String, Object> redisTemplate,
                          @Qualifier("chatObjectMapper") ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }

    /**
     * 채팅 보내기
     * @param channel
     * @param message
     */
    public void publish(String channel, ChatMessageDTO message) {
        try {
            String jsonMessage = objectMapper.writeValueAsString(message);
            redisTemplate.convertAndSend(channel, jsonMessage);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}

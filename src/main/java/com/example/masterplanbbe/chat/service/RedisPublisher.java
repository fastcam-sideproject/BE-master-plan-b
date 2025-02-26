package com.example.masterplanbbe.chat.service;

import com.example.masterplanbbe.chat.dto.ChatMessageDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisPublisher {
    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    public void publish(String channel, ChatMessageDTO message) {
        try {
            String jsonMessage = objectMapper.writeValueAsString(message);
            redisTemplate.convertAndSend(channel, jsonMessage);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}

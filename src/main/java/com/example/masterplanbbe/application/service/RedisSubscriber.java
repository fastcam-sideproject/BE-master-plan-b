package com.example.masterplanbbe.application.service;

import com.example.masterplanbbe.application.dto.ChatRedisDto;
import com.example.masterplanbbe.infrastructure.exception.chat.ChatRedisSubscribeException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RedisSubscriber implements MessageListener {

    private final SimpMessagingTemplate messagingTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public RedisSubscriber(SimpMessagingTemplate messagingTemplate,
                           @Qualifier("chatObjectMapper") ObjectMapper objectMapper) {
        this.messagingTemplate = messagingTemplate;
        this.objectMapper = objectMapper;
    }

    /**
     * 채팅 받기
     *
     * @param message
     * @param pattern
     */
    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            String msgBody = new String(message.getBody());
            ChatRedisDto chatRedisDto = objectMapper.readValue(msgBody, ChatRedisDto.class);
            messagingTemplate.convertAndSend("/sub/chat/" + chatRedisDto.specId(), chatRedisDto);
        } catch (Exception e) {
            log.error("Redis 메시지 수신 및 처리 실패: {}", e.getMessage(), e);
            throw new ChatRedisSubscribeException(); // 구독 실패 시 커스텀 예외 던짐
        }
    }
}

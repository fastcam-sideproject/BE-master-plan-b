package com.example.masterplanbbe.chat.service;

import com.example.masterplanbbe.chat.ChatMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@Service
@Slf4j
@RequiredArgsConstructor
public class RedisSubscriber implements MessageListener {

    private final SimpMessagingTemplate messagingTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            String msgBody = new String(message.getBody()); //메시지 내용
            ChatMessage chatMessage = objectMapper.readValue(msgBody, ChatMessage.class);
            messagingTemplate.convertAndSend("/sub/chat/" + chatMessage.getExamId(), chatMessage);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}

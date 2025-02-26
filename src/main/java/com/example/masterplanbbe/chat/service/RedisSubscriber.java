package com.example.masterplanbbe.chat.service;

import com.example.masterplanbbe.chat.dto.ChatMessageDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisSubscriber implements MessageListener {

    private final SimpMessagingTemplate messagingTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            String msgBody = new String(message.getBody());
            ChatMessageDTO chatMessage = objectMapper.readValue(msgBody, ChatMessageDTO.class);

            messagingTemplate.convertAndSend("/sub/chat/" + chatMessage.getExamId(), chatMessage);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}

package com.example.masterplanbbe.chat.controller;


import com.example.masterplanbbe.chat.ChatMessage;
import com.example.masterplanbbe.chat.repository.RedisChatRepository;
import com.example.masterplanbbe.chat.service.RedisPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final RedisPublisher redisPublisher;
    private final RedisChatRepository redisChatRepository;

    @MessageMapping("/chat")
    public void sendMessage(ChatMessage message) {
        Long examId = message.getExamId();

        // Redis에 저장 (MySQL로 이동하는 로직 포함)
        redisChatRepository.saveMessage(String.valueOf(examId), message);

        // Redis Pub/Sub으로 메시지 전송
        redisPublisher.publish("chat:" + examId, message);
    }
}
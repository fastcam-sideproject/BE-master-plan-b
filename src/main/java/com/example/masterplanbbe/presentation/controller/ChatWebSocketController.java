package com.example.masterplanbbe.presentation.controller;

import com.example.masterplanbbe.application.dto.ChatMessageDTO;
import com.example.masterplanbbe.application.service.ChatService;
import com.example.masterplanbbe.application.service.RedisPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatWebSocketController {

    private final RedisPublisher redisPublisher;
    private final ChatService chatService;

    @MessageMapping("/chat")
    public void sendMessage(ChatMessageDTO message) {
        Long specId = message.getSpecId();
        chatService.saveChatMessage(message);
        redisPublisher.publish("spec:" + specId, message);
    }
}
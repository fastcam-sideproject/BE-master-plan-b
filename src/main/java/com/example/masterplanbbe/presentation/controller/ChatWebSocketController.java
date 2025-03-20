package com.example.masterplanbbe.presentation.controller;

import com.example.masterplanbbe.application.service.ChatService;
import com.example.masterplanbbe.application.service.RedisPublisher;
import com.example.masterplanbbe.presentation.request.ChatRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatWebSocketController {

    private final RedisPublisher redisPublisher;
    private final ChatService chatService;

    @MessageMapping("/chat")
    public void sendMessage(ChatRequest chatRequest) {
        Long specId = chatRequest.specId();
        chatService.saveChatMessage(chatRequest);
        redisPublisher.publish("spec:" + specId, chatRequest);
    }
}
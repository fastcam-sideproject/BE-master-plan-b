package com.example.masterplanbbe.presentation.controller;

import com.example.masterplanbbe.application.service.ChatService;
import com.example.masterplanbbe.presentation.request.ChatRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatWebSocketController {

    private final ChatService chatService;

    @MessageMapping("/chat")
    public void sendMessage(@Valid ChatRequest chatRequest) {
        chatService.saveAndPublishChatMessage(chatRequest);
    }
}
package com.example.masterplanbbe.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@RequiredArgsConstructor
public class WebSocketEventListener {

    private final ChatService chatService;

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        Long memberId = (Long) headerAccessor.getSessionAttributes().get("memberId");
        Long specId = (Long) headerAccessor.getSessionAttributes().get("specId");

        if (memberId != null && specId != null) {
            chatService.leaveChatRoom(specId, memberId);
        }
    }
}

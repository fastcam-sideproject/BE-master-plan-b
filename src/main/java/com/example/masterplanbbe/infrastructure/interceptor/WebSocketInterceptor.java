package com.example.masterplanbbe.infrastructure.interceptor;

import com.example.masterplanbbe.application.service.ChatService;
import com.example.masterplanbbe.infrastructure.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketInterceptor implements ChannelInterceptor {

    private final JwtService jwtService;
    private final ChatService chatService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if(accessor == null) {
            return message;
        }

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            String jwtToken = accessor.getFirstNativeHeader("Authorization");

            if (jwtToken != null && !jwtToken.startsWith("Bearer ")) {
                throw new IllegalArgumentException("Unauthorized WebSocket connection");
            }

            jwtToken = Objects.requireNonNull(jwtToken).substring(7); // "Bearer " 제거

            try {
                JwtService.MemberPayload memberPayload = jwtService.validateAccessToken(jwtToken);
                accessor.setUser(memberPayload::getEmail);
            } catch (Exception e) {
                log.error("WebSocket 인증 실패: {}", e.getMessage());
                //Custom Exception 생성 필요
            }
        }

        if(StompCommand.SUBSCRIBE.equals(accessor.getCommand())) {
            String destination = accessor.getDestination(); // 구독 대상 경로
            String memberIdString = accessor.getFirstNativeHeader("memberId"); // 클라이언트에서 전달하는 memberId 헤더
            String specIdString = extractSpecIdFromDestination(destination);

            if (memberIdString == null || specIdString == null) {
                throw new IllegalArgumentException("Invalid WebSocket subscription");
            }

            try {
                Long memberId = Long.valueOf(memberIdString);
                Long specId = Long.valueOf(specIdString);

                accessor.getSessionAttributes().put("memberId", memberId);
                accessor.getSessionAttributes().put("specId", specId);

                chatService.enterChatRoom(specId, memberId);
            } catch (Exception e) {
                e.printStackTrace(); // 스택 트레이스 출력
                throw e;
            }
        }

        return message;
    }

    private String extractSpecIdFromDestination(String destination) {
        if (destination != null && destination.startsWith("/sub/chat/")) {
            return destination.replace("/sub/chat/", "");
        }
        return null;
    }
}

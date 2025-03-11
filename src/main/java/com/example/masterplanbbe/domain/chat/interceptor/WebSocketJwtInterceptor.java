package com.example.masterplanbbe.domain.chat.interceptor;

import com.example.masterplanbbe.common.security.jwt.JwtService;
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
public class WebSocketJwtInterceptor implements ChannelInterceptor {

    private final JwtService jwtService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (accessor != null && StompCommand.CONNECT.equals(accessor.getCommand())) {
            String jwtToken = accessor.getFirstNativeHeader("Authorization");

            if (jwtToken != null && !jwtToken.startsWith("Bearer ")) {
                log.warn("WebSocket 연결 시 JWT 토큰 없음");
                throw new IllegalArgumentException("Unauthorized WebSocket connection");
            }

            jwtToken = Objects.requireNonNull(jwtToken).substring(7); // "Bearer " 제거

            try {
                JwtService.MemberPayload memberPayload = jwtService.validateAccessToken(jwtToken);
                accessor.setUser(memberPayload::getEmail);
                log.info("WebSocket 인증 성공 - 사용자 ID: {}", memberPayload.getEmail());
            } catch (Exception e) {
                log.error("WebSocket 인증 실패: {}", e.getMessage());
                //Custom Exception 생성 필요
            }
        }
        return message;
    }
}

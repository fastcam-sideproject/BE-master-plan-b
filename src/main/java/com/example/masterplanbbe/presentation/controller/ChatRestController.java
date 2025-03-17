package com.example.masterplanbbe.presentation.controller;

import com.example.masterplanbbe.application.service.ChatService;
import com.example.masterplanbbe.domain.enums.MemberRoleEnum;
import com.example.masterplanbbe.infrastructure.security.jwt.JwtService;
import com.example.masterplanbbe.presentation.response.ChatResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/chat")
@RequiredArgsConstructor
public class ChatRestController {

    private final ChatService chatService;
    private final JwtService jwtService;

    @GetMapping("/recent")
    public ResponseEntity<?> getRecentMessages(@RequestParam Long specId,
                                               @RequestParam(defaultValue = "50") int size) {
        List<ChatResponse> recentMessages = chatService.getRecentMessages(specId, size);
        return ResponseEntity.ok().body(recentMessages);
    }

    @GetMapping
    public ResponseEntity<?> getChatMessage(@RequestParam Long lastChatId,
                                            @RequestParam Long specId,
                                            @RequestParam(defaultValue = "50") int size) {
        if (size <= 0) {
            return ResponseEntity.badRequest().body("사이즈는 0 보다 커야합니다.");
        }
        Pageable pageable = PageRequest.of(0, Math.min(size, 100)); // 최대 사이즈 100으로 제한
        return ResponseEntity.ok().body(chatService.getChatMessage(lastChatId, specId, pageable));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteChat(@RequestParam Long specId,
                                        @RequestParam Long chatId,
                                        @RequestParam Long memberId,
                                        @RequestHeader("Authorization") String token) {
        MemberRoleEnum role = jwtService.getRoleFromAccessToken(token);
        boolean deleted = chatService.deleteChat(specId, chatId, memberId, role);
        if(!deleted) {
            //삭제 실패
        }
        return ResponseEntity.ok().body("채팅 메시지가 성공적으로 삭제되었습니다.");
    }
}
package com.example.masterplanbbe.presentation.controller;

import com.example.masterplanbbe.application.service.ChatService;
import com.example.masterplanbbe.domain.enums.MemberRoleEnum;
import com.example.masterplanbbe.infrastructure.security.jwt.JwtService;
import com.example.masterplanbbe.presentation.response.ChatResponse;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
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
    public ResponseEntity<?> getRecentChatsFromRedis(@RequestParam Long specId,
                                                     @RequestParam(defaultValue = "50") int size) {
        List<ChatResponse> recentMessages = chatService.getRecentChatsFromRedis(specId, size);
        return ResponseEntity.ok().body(recentMessages);
    }

    @GetMapping
    public ResponseEntity<?> getChatsFromMySQL(@RequestParam Long lastChatId,
                                               @RequestParam Long specId,
                                               @RequestParam(defaultValue = "50") @Min(1) @Max(100) int size) {
        Pageable pageable = PageRequest.of(0, size);
        Slice<ChatResponse> chatMessages = chatService.getChatMessage(lastChatId, specId, pageable);
        return ResponseEntity.ok().body(chatMessages);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteChat(@RequestParam Long specId,
                                        @RequestParam Long chatId,
                                        @RequestParam Long memberId,
                                        @RequestHeader("Authorization") String token) {
        MemberRoleEnum role = jwtService.getRoleFromAccessToken(token);
        boolean deleted = chatService.deleteChat(specId, chatId, memberId, role);
        if (!deleted) {
            //삭제 실패
        }
        return ResponseEntity.ok().body("채팅 메시지가 성공적으로 삭제되었습니다.");
    }
}
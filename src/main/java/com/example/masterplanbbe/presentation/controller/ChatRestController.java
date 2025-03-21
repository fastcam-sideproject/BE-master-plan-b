package com.example.masterplanbbe.presentation.controller;

import com.example.masterplanbbe.application.service.ChatService;
import com.example.masterplanbbe.domain.enums.MemberRoleEnum;
import com.example.masterplanbbe.infrastructure.security.jwt.JwtService;
import com.example.masterplanbbe.presentation.response.ChatResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Chat controller api", description = "채팅 API")
@RestController
@RequestMapping("/api/v1/chat")
@RequiredArgsConstructor
public class ChatRestController {

    private final ChatService chatService;
    private final JwtService jwtService;

    @Operation(summary = "Redis에서 최신 채팅 메시지 가져오기")
    @GetMapping("/{specId}/recent")
    public ResponseEntity<List<ChatResponse>> getRecentChatsFromRedis(@PathVariable("specId") Long specId,
                                                                      @RequestParam(name = "size", defaultValue = "50") int size) {
        List<ChatResponse> recentMessages = chatService.getRecentChatsFromRedis(specId, size);
        return ResponseEntity.ok(recentMessages);
    }


    @Operation(summary = "MySQL에서 채팅 메시지 가져오기")
    @GetMapping("/{specId}")
    public ResponseEntity<Slice<ChatResponse>> getChatsFromMySQL(@PathVariable("specId") Long specId,
                                                                 @RequestParam(name = "lastChatId") Long lastChatId,
                                                                 @RequestParam(defaultValue = "50") @Min(1) @Max(100) int size) {
        Pageable pageable = PageRequest.of(0, size);
        Slice<ChatResponse> chatMessages = chatService.getChatMessage(lastChatId, specId, pageable);
        return ResponseEntity.ok(chatMessages);
    }

    @Operation(summary = "채팅 메시지 삭제하기")
    @DeleteMapping("/{specId}/{chatId}")
    public ResponseEntity<String> deleteChat(@PathVariable("specId") Long specId,
                                             @PathVariable("chatId") Long chatId,
                                             @RequestParam(name = "memberId") Long memberId,
                                             @RequestHeader("Authorization") String token) {
        MemberRoleEnum role = jwtService.getRoleFromAccessToken(token);
        boolean deleted = chatService.deleteChat(specId, chatId, memberId, role);
        if (!deleted) {
            return ResponseEntity.badRequest().body("삭제할 수 없는 메시지입니다.");
        }
        return ResponseEntity.ok("채팅 메시지가 성공적으로 삭제되었습니다.");
    }

    @Operation(summary = "채팅방 사용자 수 조회")
    @GetMapping("/{specId}/users/count")
    public ResponseEntity<Long> getUserCount(@PathVariable("specId") Long specId) {
        Long chatRoomMemberCount = chatService.getChatRoomMemberCount(specId);
        return ResponseEntity.ok(chatRoomMemberCount);
    }
}
package com.example.masterplanbbe.presentation.controller;

import com.example.masterplanbbe.application.dto.ChatMessageDTO;
import com.example.masterplanbbe.application.service.ChatService;
import com.example.masterplanbbe.application.service.RedisPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/chat")
@RequiredArgsConstructor
public class ChatController {

    private final RedisPublisher redisPublisher;
    private final ChatService chatService;

    @MessageMapping("/chat")
    public void sendMessage(ChatMessageDTO message) {
        Long specId = message.getSpecId();
        chatService.saveChatMessage(message);
        redisPublisher.publish("spec:" + specId, message);
    }

    @GetMapping("/recent")
    public ResponseEntity<?> getRecentMessages(@RequestParam Long specId) {
        return ResponseEntity.ok()
                .body(chatService.getRecentMessages(specId));
    }

    @GetMapping
    public ResponseEntity<?> getChatMessage(@RequestParam Long lastChatId,
                                            @RequestParam Long specId,
                                            @RequestParam(defaultValue = "50") int size) {
        if (size <= 0) {
            return ResponseEntity.badRequest().body("사이즈는 0 보다 커야합니다.");
        }
        Pageable pageable = PageRequest.of(0, Math.min(size, 100)); //최대 사이즈 100으로 제한
        return ResponseEntity.ok().body(chatService.getChatMessage(lastChatId, specId, pageable));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteChat(@RequestParam Long specId,
                                        @RequestParam Long chatId,
                                        @RequestParam Long memberId,
                                        @RequestParam String role) {
        chatService.deleteChat(specId, chatId, memberId, role);
        return ResponseEntity.ok().body("채팅 메시지가 성공적으로 삭제되었습니다.");
    }
}

package com.example.masterplanbbe.chat.controller;

import com.example.masterplanbbe.chat.dto.ChatMessageDTO;
import com.example.masterplanbbe.chat.service.ChatService;
import com.example.masterplanbbe.chat.service.RedisPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {

    private final RedisPublisher redisPublisher;
    private final ChatService chatService;

    @MessageMapping("/chat")
    public void sendMessage(ChatMessageDTO message) {
        Long specId = message.getSpecId();
        chatService.saveChatMessage(message);
        redisPublisher.publish("spec:" + specId, message); // Redis Pub/Sub을 통해 메시지 전송
    }

    @GetMapping("/recent")
    public ResponseEntity<?> getRecentMessages(@RequestParam Long specId) {
        return ResponseEntity.ok()
                .body(chatService.getRecentMessages(specId));
    }

    @DeleteMapping("/{specId}/{chatId}")
    public ResponseEntity<?> deleteChat(@PathVariable Long specId, @PathVariable Long chatId, @RequestParam Long memberId, @RequestParam String role) {
        chatService.deleteChat(specId, chatId, memberId, role);
        return ResponseEntity.ok().body("채팅 메시지가 성공적으로 삭제되었습니다.");
    }
}

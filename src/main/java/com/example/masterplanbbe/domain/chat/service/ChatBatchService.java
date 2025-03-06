package com.example.masterplanbbe.domain.chat.service;

import com.example.masterplanbbe.domain.chat.ChatMessage;
import com.example.masterplanbbe.domain.chat.repository.BatchChatRepository;
import com.example.masterplanbbe.domain.chat.repository.RedisChatRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatBatchService {
    private static final int MAX_MESSAGES = 2; // 채팅 메시지 제한 개수

    private final RedisChatRepository redisChatRepository;
    private final BatchChatRepository batchChatRepository;
    private final ObjectMapper objectMapper;

    @Transactional
    @Scheduled(fixedRate = 600_000)
    public void batchSaveChatMessages() {
        List<ChatMessage> chatMessages = new ArrayList<>();
        List<String> chatRooms = redisChatRepository.getAllChatRooms();
        for (String chatKey : chatRooms) {
            try {
                String specIdStr = chatKey.replace("spec:", "");
                Long specId = Long.parseLong(specIdStr);

                if (redisChatRepository.getMessageCount(specId) > MAX_MESSAGES) {
                    chatMessages.addAll(findOldMessages(specId));
                }
            } catch (NumberFormatException e) {
                log.error(e.getMessage());
            }
        }
        batchChatRepository.saveAll(chatMessages);
    }

    private List<ChatMessage> findOldMessages(Long specId) {
        List<ChatMessage> chatMessages = new ArrayList<>();
        try {
            List<String> oldMessages = redisChatRepository.getMessagesInRange(specId, MAX_MESSAGES, -1);

            if (oldMessages != null && !oldMessages.isEmpty()) {
                for (String jsonMessage : oldMessages) {
                    try {
                        ChatMessage chatMessage = objectMapper.readValue(jsonMessage, ChatMessage.class);
                        chatMessages.add(chatMessage);
                    } catch (JsonProcessingException e) {
                        log.error(e.getMessage());
                    }
                }
            }
            redisChatRepository.trimMessages(specId, MAX_MESSAGES);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return chatMessages;
    }
}

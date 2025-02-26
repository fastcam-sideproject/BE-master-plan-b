package com.example.masterplanbbe.chat.service;

import com.example.masterplanbbe.chat.ChatLog;
import com.example.masterplanbbe.chat.repository.ChatLogRepository;
import com.example.masterplanbbe.chat.repository.RedisChatRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatBatchService {
    private static final int MAX_MESSAGES = 50; // 메시지 제한 개수

    private final RedisChatRepository redisChatRepository;
    private final ChatLogRepository chatLogRepository;
    private final ObjectMapper objectMapper;

    /**
     * 5초마다 배치 실행
     */
    @Scheduled(fixedRate = 5000)
    public void batchSaveChatMessages() {
        List<String> chatRooms = redisChatRepository.getAllChatRooms();
        for (String chatKey : chatRooms) {
            try {
                String examIdStr = chatKey.replace("chat:", "");
                Long examId = Long.parseLong(examIdStr);

                if (redisChatRepository.getMessageCount(examId) > MAX_MESSAGES) {
                    moveOldMessagesToMySQL(examId);
                }
            } catch (NumberFormatException e) {
                log.error(e.getMessage());
            }
        }
    }

    private void moveOldMessagesToMySQL(Long examId) {
        try {
            List<String> oldMessages = redisChatRepository.getMessagesInRange(examId, MAX_MESSAGES, -1);

            if (oldMessages != null && !oldMessages.isEmpty()) {
                for (String jsonMessage : oldMessages) {
                    try {
                        ChatLog chatLog = objectMapper.readValue(jsonMessage, ChatLog.class);
                        chatLogRepository.save(chatLog);
                    } catch (JsonProcessingException e) {
                        log.error(e.getMessage());
                    }
                }
            }
            redisChatRepository.trimMessages(examId, MAX_MESSAGES);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}

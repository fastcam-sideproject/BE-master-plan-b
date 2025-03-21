package com.example.masterplanbbe.application.service;

import com.example.masterplanbbe.application.dto.ChatRedisDto;
import com.example.masterplanbbe.domain.entity.ChatMessage;
import com.example.masterplanbbe.domain.repository.ChatBatchRepositoryPort;
import com.example.masterplanbbe.infrastructure.repository.ChatRedisRepositoryAdapter;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ChatBatchService {
    private static final int MAX_MESSAGES = 2; // 채팅 메시지 제한 개수

    private final ChatRedisRepositoryAdapter chatRedisRepositoryAdapter;
    private final ChatBatchRepositoryPort chatBatchRepository;

    @Autowired
    public ChatBatchService(ChatRedisRepositoryAdapter chatRedisRepositoryAdapter,
                            ChatBatchRepositoryPort chatBatchRepository) {
        this.chatRedisRepositoryAdapter = chatRedisRepositoryAdapter;
        this.chatBatchRepository = chatBatchRepository;
    }

    @Transactional
    @Scheduled(fixedRate = 600_000)
    public void batchSaveChatMessages() {
        List<ChatMessage> chatMessages = new ArrayList<>();
        List<String> chatRooms = chatRedisRepositoryAdapter.getAllChatRooms();
        for (String chatKey : chatRooms) {
            try {
                String specIdStr = chatKey.replace("spec:", "");
                Long specId = Long.parseLong(specIdStr);

                if (chatRedisRepositoryAdapter.getMessageCount(specId) > MAX_MESSAGES) {
                    chatMessages.addAll(findOldMessages(specId));
                }
            } catch (NumberFormatException e) {
                log.error(e.getMessage());
            }
        }
        chatBatchRepository.saveAll(chatMessages);
    }

    private List<ChatMessage> findOldMessages(Long specId) {
        List<ChatMessage> chatMessages = new ArrayList<>();
        try {
            List<ChatRedisDto> oldMessages = chatRedisRepositoryAdapter.getMessagesInRange(specId, MAX_MESSAGES, -1);

            if (oldMessages != null && !oldMessages.isEmpty()) {
                for (ChatRedisDto message : oldMessages) {
                    chatMessages.add(ChatMessage.from(message));
                }
            }
            chatRedisRepositoryAdapter.trimMessages(specId, MAX_MESSAGES);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return chatMessages;
    }
}

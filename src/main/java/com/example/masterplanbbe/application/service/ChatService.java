package com.example.masterplanbbe.application.service;

import com.example.masterplanbbe.domain.entity.ChatMessage;
import com.example.masterplanbbe.application.dto.ChatMessageDTO;
import com.example.masterplanbbe.domain.repository.ChatMessageRepository;
import com.example.masterplanbbe.infrastructure.repository.RedisChatRepository;
import com.example.masterplanbbe.infrastructure.util.SnowflakeIdGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ChatService {
    private final ChatMessageRepository chatMessageRepository;
    private final RedisChatRepository redisChatRepository;
    private final SnowflakeIdGenerator snowflakeIdGenerator;
    private final ObjectMapper objectMapper;

    @Autowired
    public ChatService(ChatMessageRepository chatMessageRepository,
                       RedisChatRepository redisChatRepository,
                       SnowflakeIdGenerator snowflakeIdGenerator,
                       @Qualifier("chatObjectMapper") ObjectMapper objectMapper) {
        this.chatMessageRepository = chatMessageRepository;
        this.redisChatRepository = redisChatRepository;
        this.snowflakeIdGenerator = snowflakeIdGenerator;
        this.objectMapper = objectMapper;
    }

    /**
     * 채팅을 Redis에 저장
     */
    public void saveChatMessage(ChatMessageDTO message) {
        try {
            long chatId = snowflakeIdGenerator.nextId();
            message.setId(chatId);

            String jsonMessage = objectMapper.writeValueAsString(message);
            redisChatRepository.saveMessage(message.getSpecId(), jsonMessage);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 가장 최신 채팅 조회
     *
     * @param specId
     * @return
     */
    public List<ChatMessageDTO> getRecentMessages(Long specId) {
        try {
            List<String> messages = redisChatRepository.getMessagesInRange(specId, 0, -1);
            return messages.stream()
                    .map(msg -> {
                        try {
                            return objectMapper.readValue(msg, ChatMessageDTO.class);
                        } catch (Exception e) {
                            log.error(e.getMessage());
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error(e.getMessage());
            return List.of();
        }
    }

    /**
     * 메시지 50개 조회
     *
     * @param lastChatId 조회 기준이 될 채팅의 ID
     * @param specId
     * @param pageable
     */
    public Slice<ChatMessage> getChatMessage(Long lastChatId, Long specId, Pageable pageable) {
        return chatMessageRepository.findChatList(lastChatId, specId, pageable);
    }

    /**
     * 채팅 메시지 삭제 (본인 또는 관리자만 삭제 가능)
     */
    public boolean deleteChat(Long specId, Long chatId, Long memberId, String role) {
        boolean isDeleted = deleteFromRedis(specId, chatId, memberId, role);
        if (!isDeleted) {
            isDeleted = deleteFromMySQL(chatId, memberId, role);
        }
        return isDeleted;
    }

    /**
     * Redis에서 채팅 삭제 (배치 처리 전 메시지)
     */
    private boolean deleteFromRedis(Long specId, Long chatId, Long memberId, String role) {
        try {
            List<String> messages = redisChatRepository.getMessagesInRange(specId, 0, -1);
            for (String msg : messages) {
                ChatMessageDTO chatMessage = objectMapper.readValue(msg, ChatMessageDTO.class);
                if (chatMessage.getId().equals(chatId) &&
                        (chatMessage.getMemberId().equals(memberId) || "ADMIN".equals(role))) {
                    redisChatRepository.deleteMessage(specId, msg);
                    return true;
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return false;
    }

    /**
     * MySQL에서 채팅 삭제 (배치 처리 후 메시지)
     */
    private boolean deleteFromMySQL(Long chatId, Long memberId, String role) {
        try {
            ChatMessage chatMessage = chatMessageRepository.findById(chatId).orElse(null);
            if (chatMessage != null && (chatMessage.getMemberId().equals(memberId) || "ADMIN".equals(role))) {
                chatMessageRepository.delete(chatMessage);
                return true;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return false;
    }
}

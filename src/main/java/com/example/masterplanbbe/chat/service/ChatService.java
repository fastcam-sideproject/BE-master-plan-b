package com.example.masterplanbbe.chat.service;

import com.example.masterplanbbe.chat.ChatLog;
import com.example.masterplanbbe.chat.dto.ChatMessageDTO;
import com.example.masterplanbbe.chat.repository.ChatLogRepository;
import com.example.masterplanbbe.chat.repository.RedisChatRepository;
import com.example.masterplanbbe.chat.util.SnowflakeIdGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatLogRepository chatLogRepository;
    private final RedisChatRepository redisChatRepository;
    private final SnowflakeIdGenerator snowflakeIdGenerator;
    private final ObjectMapper objectMapper;

    /**
     * 채팅 메시지를 Redis에 저장 (MySQL은 배치 처리)
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

    public List<ChatMessageDTO> getRecentMessages(Long examId) {
        try {
            List<String> messages = redisChatRepository.getMessagesInRange(examId, 0, -1);
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
     * 채팅 메시지 삭제 (본인 또는 관리자만 삭제 가능)
     */
    public boolean deleteChat(Long examId, Long chatId, Long memberId, String role) {
        boolean isDeleted = deleteFromRedis(examId, chatId, memberId, role);
        if (!isDeleted) {
            isDeleted = deleteFromMySQL(chatId, memberId, role);
        }
        return isDeleted;
    }

    /**
     * Redis에서 채팅 삭제 (배치 처리 전 메시지)
     */
    private boolean deleteFromRedis(Long examId, Long chatId, Long memberId, String role) {
        try {
            List<String> messages = redisChatRepository.getMessagesInRange(examId, 0, -1);
            for (String msg : messages) {
                ChatMessageDTO chatMessage = objectMapper.readValue(msg, ChatMessageDTO.class);
                if (chatMessage.getId().equals(chatId) &&
                        (chatMessage.getMemberId().equals(memberId) || "ADMIN".equals(role))) {
                    redisChatRepository.deleteMessage(examId, msg);
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
            ChatLog chatLog = chatLogRepository.findById(chatId).orElse(null);
            if (chatLog != null && (chatLog.getMemberId().equals(memberId) || "ADMIN".equals(role))) {
                chatLogRepository.delete(chatLog);
                return true;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return false;
    }
}

package com.example.masterplanbbe.application.service;

import com.example.masterplanbbe.application.dto.ChatRedisDto;
import com.example.masterplanbbe.domain.entity.ChatMessage;
import com.example.masterplanbbe.domain.enums.MemberRoleEnum;
import com.example.masterplanbbe.domain.repository.ChatMessageRepository;
import com.example.masterplanbbe.infrastructure.repository.RedisChatRepository;
import com.example.masterplanbbe.infrastructure.util.SnowflakeIdGenerator;
import com.example.masterplanbbe.presentation.request.ChatRequest;
import com.example.masterplanbbe.presentation.response.ChatResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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
    public void saveChatMessage(ChatRequest chatRequest) {
        try {
            long snowflakeId = snowflakeIdGenerator.nextId();
            ChatRedisDto chatRedisDto = ChatRedisDto.from(snowflakeId, chatRequest);
            String jsonMessage = objectMapper.writeValueAsString(chatRedisDto);
            redisChatRepository.saveMessage(chatRedisDto.specId(), jsonMessage);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 가장 최신 채팅 조회
     */
    public List<ChatResponse> getRecentMessages(Long specId, int size) {
        try {
            List<String> messages = redisChatRepository.getMessagesInRange(specId, 0, (size - 1));
            return messages.stream().map(msg -> {
                        try {
                            ChatRedisDto chatRedisDto = objectMapper.readValue(msg, ChatRedisDto.class);
                            return ChatResponse.from(chatRedisDto);
                        } catch (Exception e) {
                            log.error(e.getMessage());
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .toList();
        } catch (Exception e) {
            log.error(e.getMessage());
            return List.of();
        }
    }

    /**
     * MySQL에서 메시지 조회
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
    public boolean deleteChat(Long specId, Long chatId, Long memberId, MemberRoleEnum role) {
        if (deleteFromRedis(specId, chatId, memberId, role)) {
            return true; //레디스에서 삭제 성공시 종료
        }
        return deleteFromMySQL(chatId, memberId, role);
    }

    /**
     * Redis에서 채팅 삭제 (배치 처리 전 메시지)
     */
    private boolean deleteFromRedis(Long specId, Long chatId, Long memberId, MemberRoleEnum role) {
        try {
            List<String> messages = redisChatRepository.getMessagesInRange(specId, 0, -1);
            for (String msg : messages) {
                ChatRedisDto chatRedisDto = objectMapper.readValue(msg, ChatRedisDto.class);
                if (role == MemberRoleEnum.ADMIN ||
                        chatRedisDto.id().equals(chatId) && (chatRedisDto.memberId().equals(memberId))) {
                    Long removeCount = redisChatRepository.deleteMessage(specId, msg);
                    return removeCount != null && removeCount != 0;
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
    private boolean deleteFromMySQL(Long chatId, Long memberId, MemberRoleEnum role) {
        try {
            ChatMessage chatMessage = chatMessageRepository.findById(chatId).orElse(null);
            if (role == MemberRoleEnum.ADMIN ||
                    chatMessage != null && (chatMessage.getMemberId().equals(memberId))) {
                chatMessageRepository.delete(chatMessage);
                return true;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return false;
    }
}

package com.example.masterplanbbe.application.service;

import com.example.masterplanbbe.application.dto.ChatRedisDto;
import com.example.masterplanbbe.domain.entity.ChatMessage;
import com.example.masterplanbbe.domain.enums.MemberRoleEnum;
import com.example.masterplanbbe.domain.repository.ChatMessageRepository;
import com.example.masterplanbbe.infrastructure.repository.ChatRedisRepositoryAdapter;
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
    private final ChatRedisRepositoryAdapter chatRedisRepositoryAdapter;
    private final SnowflakeIdGenerator snowflakeIdGenerator;
    private final RedisPublisher redisPublisher;

    @Autowired
    public ChatService(ChatMessageRepository chatMessageRepository,
                       ChatRedisRepositoryAdapter chatRedisRepositoryAdapter,
                       SnowflakeIdGenerator snowflakeIdGenerator,
                       RedisPublisher redisPublisher) {
        this.chatMessageRepository = chatMessageRepository;
        this.chatRedisRepositoryAdapter = chatRedisRepositoryAdapter;
        this.snowflakeIdGenerator = snowflakeIdGenerator;
        this.redisPublisher = redisPublisher;
    }

    /**
     * 채팅을 Redis에 저장
     */
    public void saveAndPublishChatMessage(ChatRequest chatRequest) {
        try {
            long snowflakeId = snowflakeIdGenerator.nextId();
            ChatRedisDto chatRedisDto = ChatRedisDto.from(snowflakeId, chatRequest);

            redisPublisher.publish("spec:" + chatRedisDto.specId(), chatRedisDto);

            chatRedisRepositoryAdapter.saveMessage(chatRedisDto.specId(), chatRedisDto);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 가장 최신 채팅 조회
     */
    public List<ChatResponse> getRecentChatsFromRedis(Long specId, int size) {
        try {
            List<ChatRedisDto> messages = chatRedisRepositoryAdapter.getMessagesInRange(specId, 0, size - 1); // 🔥 JSON 변환 없이 바로 객체 가져오기
            return messages.stream()
                    .map(ChatResponse::from)
                    .toList();
        } catch (Exception e) {
            log.error("Redis에서 최신 채팅 조회 중 오류 발생: {}", e.getMessage());
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
    public Slice<ChatResponse> getChatMessage(Long lastChatId, Long specId, Pageable pageable) {
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
            List<ChatRedisDto> messages = chatRedisRepositoryAdapter.getMessagesInRange(specId, 0, -1);
            for (ChatRedisDto chatRedisDto : messages) {
                if (role == MemberRoleEnum.ADMIN ||
                        (chatRedisDto.id().equals(chatId) && chatRedisDto.memberId().equals(memberId))) {
                    Long removeCount = chatRedisRepositoryAdapter.deleteMessage(specId, chatRedisDto);
                    return removeCount != null && removeCount > 0;
                }
            }
        } catch (Exception e) {
            log.error("Redis에서 메시지 삭제 중 오류 발생: {}", e.getMessage());
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
                if (chatMessage != null) {
                    chatMessageRepository.delete(chatMessage);
                }
                return true;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return false;
    }

    public void enterChatRoom(Long specId, Long memberId) {
        chatRedisRepositoryAdapter.addUserToChatRoom(specId, memberId);
    }

    public void leaveChatRoom(Long specId, Long memberId) {
        chatRedisRepositoryAdapter.removeUserFromChatRoom(specId, memberId);
    }

    public Long getChatRoomMemberCount(Long specId) {
        return chatRedisRepositoryAdapter.getChatRoomUserCount(specId);
    }
}

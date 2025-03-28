package com.example.masterplanbbe.application.service;

import com.example.masterplanbbe.application.dto.ChatRedisDto;
import com.example.masterplanbbe.domain.entity.ChatMessage;
import com.example.masterplanbbe.domain.enums.MemberRoleEnum;
import com.example.masterplanbbe.domain.repository.ChatMessageRepository;
import com.example.masterplanbbe.infrastructure.exception.BaseException;
import com.example.masterplanbbe.infrastructure.exception.chat.*;
import com.example.masterplanbbe.infrastructure.repository.ChatRedisRepositoryAdapter;
import com.example.masterplanbbe.infrastructure.util.SnowflakeIdGenerator;
import com.example.masterplanbbe.presentation.request.ChatRequest;
import com.example.masterplanbbe.presentation.response.ChatResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;

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
            log.error("채팅 저장 중 오류 발생: {}", e.getMessage(), e);
            throw new ChatMessageSaveException();
        }
    }

    /**
     * 가장 최신 채팅 조회
     */
    public List<ChatResponse> getRecentChatsFromRedis(Long specId, int size) {
        try {
            List<ChatRedisDto> messages = chatRedisRepositoryAdapter.getMessagesInRange(specId, 0, size - 1);
            return messages.stream()
                    .map(ChatResponse::from)
                    .toList();
        } catch (Exception e) {
            log.error("Redis에서 최신 채팅 조회 중 오류 발생: {}", e.getMessage(), e);
            throw new ChatLoadException();
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
        if (specId == null || specId <= 0 || pageable == null) {
            throw new InvalidChatQueryParameterException();
        }

        try {
            return chatMessageRepository.findChatList(lastChatId, specId, pageable);
        } catch (Exception e) {
            log.error("MySQL에서 채팅 메시지 조회 중 오류 발생: {}", e.getMessage(), e);
            throw new ChatQueryException();
        }
    }

    /**
     * 채팅 메시지 삭제 (본인 또는 관리자만 삭제 가능)
     */
    public boolean deleteChat(Long specId, Long chatId, Long memberId, MemberRoleEnum role) {
        if (specId == null || chatId == null || memberId == null || role == null) {
            throw new InvalidChatDeleteRequestException();
        }
        if (deleteFromRedis(specId, chatId, memberId, role)) {
            return true;
        }
        return deleteFromMySQL(chatId, memberId, role);
    }

    /**
     * Redis에서 채팅 삭제 (배치 처리 전 메시지)
     */
    private boolean deleteFromRedis(Long specId, Long chatId, Long memberId, MemberRoleEnum role) {
        try {
            List<ChatRedisDto> messages = chatRedisRepositoryAdapter.getMessagesInRange(specId, 0, -1);
            for (ChatRedisDto dto : messages) {
                boolean isAdmin = role == MemberRoleEnum.ADMIN;
                boolean isOwner = dto.id().equals(chatId) && dto.memberId().equals(memberId);

                if (isAdmin || isOwner) {
                    Long removed = chatRedisRepositoryAdapter.deleteMessage(specId, dto);
                    if (removed != null && removed > 0) return true;
                    else throw new ChatDeleteFailedException();
                }
            }
        } catch (Exception e) {
            log.error("Redis에서 메시지 삭제 중 오류 발생: {}", e.getMessage(), e);
            throw new ChatDeleteFailedException();
        }
        return false; // Redis에서 못 지웠다면 MySQL 시도
    }

    /**
     * MySQL에서 채팅 삭제 (배치 처리 후 메시지)
     */
    private boolean deleteFromMySQL(Long chatId, Long memberId, MemberRoleEnum role) {
        try {
            ChatMessage chatMessage = chatMessageRepository.findById(chatId)
                    .orElseThrow(ChatMessageNotFoundException::new);

            boolean isAdmin = role == MemberRoleEnum.ADMIN;
            boolean isOwner = chatMessage.getMemberId().equals(memberId);

            if (!(isAdmin || isOwner)) {
                throw new ChatDeleteForbiddenException();
            }

            chatMessageRepository.delete(chatMessage);
            return true;
        } catch (BaseException e) {
            throw e; // 이미 위에서 던졌으면 그대로 전파
        } catch (Exception e) {
            log.error("MySQL에서 메시지 삭제 중 오류 발생: {}", e.getMessage(), e);
            throw new ChatDeleteFailedException();
        }
    }

    public void enterChatRoom(Long specId, Long memberId) {
        if (specId == null || memberId == null || specId <= 0 || memberId <= 0) {
            throw new InvalidChatRoomRequestException();
        }
        try {
            chatRedisRepositoryAdapter.addUserToChatRoom(specId, memberId);
        } catch (Exception e) {
            log.error("채팅방 입장 중 오류 발생: {}", e.getMessage(), e);
            throw new ChatRoomOperationException();
        }
    }

    public void leaveChatRoom(Long specId, Long memberId) {
        if (specId == null || memberId == null || specId <= 0 || memberId <= 0) {
            throw new InvalidChatRoomRequestException();
        }
        try {
            chatRedisRepositoryAdapter.removeUserFromChatRoom(specId, memberId);
        } catch (Exception e) {
            log.error("채팅방 퇴장 중 오류 발생: {}", e.getMessage(), e);
            throw new ChatRoomOperationException();
        }
    }

    public Long getChatRoomMemberCount(Long specId) {
        if (specId == null || specId <= 0) {
            throw new InvalidChatRoomRequestException();
        }
        try {
            return chatRedisRepositoryAdapter.getChatRoomUserCount(specId);
        } catch (Exception e) {
            log.error("채팅방 접속자 수 조회 중 오류 발생: {}", e.getMessage(), e);
            throw new ChatRoomOperationException();
        }
    }
}

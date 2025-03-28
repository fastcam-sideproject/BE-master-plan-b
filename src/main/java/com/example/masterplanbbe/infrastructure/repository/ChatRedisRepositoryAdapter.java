package com.example.masterplanbbe.infrastructure.repository;

import com.example.masterplanbbe.application.dto.ChatRedisDto;
import com.example.masterplanbbe.domain.repository.ChatRedisRepositoryPort;
import com.example.masterplanbbe.infrastructure.exception.chat.ChatRedisOperationException;
import com.example.masterplanbbe.infrastructure.exception.chat.InvalidChatRedisKeyException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Slf4j
@Repository
public class ChatRedisRepositoryAdapter implements ChatRedisRepositoryPort {
    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public ChatRedisRepositoryAdapter(@Qualifier("chatPubSubTemplate") RedisTemplate<String, Object> redisTemplate,
                                      @Qualifier("chatObjectMapper") ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }

    /**
     * 채팅 메시지를 Redis에 저장
     */
    public void saveMessage(Long specId, ChatRedisDto chatRedisDto) {
        if (specId == null || chatRedisDto == null) {
            throw new InvalidChatRedisKeyException();
        }
        try {
            String key = "spec:" + specId;
            redisTemplate.opsForList().leftPush(key, chatRedisDto);
        } catch (Exception e) {
            log.error("Redis 메시지 저장 실패: {}", e.getMessage(), e);
            throw new ChatRedisOperationException();
        }
    }

    /**
     * 특정 채팅방의 메시지 개수 조회
     */
    public Long getMessageCount(Long specId) {
        String key = "spec:" + specId;
        Long count = redisTemplate.opsForList().size(key);
        return count != null ? count : 0;
    }

    /**
     * 특정 범위의 채팅 메시지를 조회
     */
    public List<ChatRedisDto> getMessagesInRange(Long specId, int start, int end) {
        if (specId == null || start < 0 || end < start) {
            throw new InvalidChatRedisKeyException();
        }
        try {
            String key = "spec:" + specId;
            List<Object> rawMessages = redisTemplate.opsForList().range(key, start, end);

            return rawMessages != null
                    ? rawMessages.stream()
                    .map(obj -> objectMapper.convertValue(obj, ChatRedisDto.class))
                    .toList()
                    : List.of();
        } catch (Exception e) {
            log.error("Redis 메시지 조회 실패: {}", e.getMessage(), e);
            throw new ChatRedisOperationException();
        }
    }

    /**
     * MySQL로 이동된 메시지 삭제
     */
    public void trimMessages(Long specId, int maxMessages) {
        String key = "spec:" + specId;
        redisTemplate.opsForList().trim(key, 0, maxMessages - 1);
    }

    /**
     * 저장된 모든 채팅방 리스트 조회 (배치 서비스에서 사용)
     */
    public List<String> getAllChatRooms() {
        Set<String> keys = redisTemplate.keys("spec:*");
        return keys != null ? List.copyOf(keys) : List.of();
    }

    /**
     * 특정 채팅 메시지를 삭제
     */
    public Long deleteMessage(Long specId, ChatRedisDto chatRedisDto) {
        if (specId == null || chatRedisDto == null) {
            throw new InvalidChatRedisKeyException();
        }
        try {
            String key = "spec:" + specId;
            return redisTemplate.opsForList().remove(key, 1, chatRedisDto);
        } catch (Exception e) {
            log.error("Redis 메시지 삭제 실패: {}", e.getMessage(), e);
            throw new ChatRedisOperationException();
        }
    }

    /**
     * 사용자가 채팅방에 입장하면 Redis Set에 추가
     */
    public void addUserToChatRoom(Long specId, Long memberId) {
        if (specId == null || memberId == null) {
            throw new InvalidChatRedisKeyException();
        }
        try {
            String key = "spec_users:" + specId;
            redisTemplate.opsForSet().add(key, memberId);
        } catch (Exception e) {
            log.error("채팅방 입장 Redis 오류: {}", e.getMessage(), e);
            throw new ChatRedisOperationException();
        }
    }

    /**
     * 사용자가 채팅방에서 나가면 Redis Set에서 제거
     */
    public void removeUserFromChatRoom(Long specId, Long memberId) {
        if (specId == null || memberId == null) {
            throw new InvalidChatRedisKeyException();
        }
        try {
            String key = "spec_users:" + specId;
            redisTemplate.opsForSet().remove(key, memberId);
        } catch (Exception e) {
            log.error("채팅방 퇴장 Redis 오류: {}", e.getMessage(), e);
            throw new ChatRedisOperationException();
        }
    }

    /**
     * 특정 채팅방(specId)의 현재 접속자 수 조회
     */
    public Long getChatRoomUserCount(Long specId) {
        if (specId == null) {
            throw new InvalidChatRedisKeyException();
        }
        try {
            String key = "spec_users:" + specId;
            return redisTemplate.opsForSet().size(key);
        } catch (Exception e) {
            log.error("채팅방 접속자 수 조회 실패: {}", e.getMessage(), e);
            throw new ChatRedisOperationException();
        }
    }
}

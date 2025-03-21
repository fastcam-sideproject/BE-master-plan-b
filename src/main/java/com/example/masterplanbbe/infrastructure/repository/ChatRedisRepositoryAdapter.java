package com.example.masterplanbbe.infrastructure.repository;

import com.example.masterplanbbe.application.dto.ChatRedisDto;
import com.example.masterplanbbe.domain.repository.ChatRedisRepositoryPort;
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
    private final RedisTemplate<String, String> stringRedisTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public ChatRedisRepositoryAdapter(@Qualifier("chatPubSubTemplate") RedisTemplate<String, Object> redisTemplate,
                                      @Qualifier("authTemplate") RedisTemplate<String, String> stringRedisTemplate,
                                      @Qualifier("chatObjectMapper") ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.stringRedisTemplate = stringRedisTemplate;
        this.objectMapper = objectMapper;
    }

    /**
     * 채팅 메시지를 Redis에 저장
     */
    public void saveMessage(Long specId, ChatRedisDto chatRedisDto) {
        String key = "spec:" + specId;
        redisTemplate.opsForList().leftPush(key, chatRedisDto);
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
        String key = "spec:" + specId;
        List<Object> rawMessages = redisTemplate.opsForList().range(key, start, end);

        return rawMessages != null ? rawMessages.stream()
                .map(obj -> objectMapper.convertValue(obj, ChatRedisDto.class)).toList() : List.of();
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
        String key = "spec:" + specId;
        //TTL 설정 만료에 따라 직렬화된 객체 형태의 차이로 삭제가 안될수도 있으니 개선 필요
        return redisTemplate.opsForList().remove(key, 1, chatRedisDto);
    }

    /**
     * 사용자가 채팅방에 입장하면 Redis Set에 추가
     */
    public void addUserToChatRoom(Long specId, Long memberId) {
        String key = "spec_users:" + specId;
        redisTemplate.opsForSet().add(key, memberId);
    }

    /**
     * 사용자가 채팅방에서 나가면 Redis Set에서 제거
     */
    public void removeUserFromChatRoom(Long specId, Long memberId) {
        String key = "spec_users:" + specId;
        redisTemplate.opsForSet().remove(key, memberId);
    }

    /**
     * 특정 채팅방(specId)의 현재 접속자 수 조회
     */
    public Long getChatRoomUserCount(Long specId) {
        String key = "spec_users:" + specId;
        return redisTemplate.opsForSet().size(key);
    }
}

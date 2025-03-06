package com.example.masterplanbbe.domain.chat.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Set;

@Slf4j
@Repository
public class RedisChatRepository {
    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public RedisChatRepository(@Qualifier("chatTemplate") RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 채팅 메시지를 Redis에 저장
     */
    public void saveMessage(Long specId, String jsonMessage) {
        String key = "spec:" + specId;
        redisTemplate.opsForList().leftPush(key, jsonMessage);
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
    public List<String> getMessagesInRange(Long specId, int start, int end) {
        String key = "spec:" + specId;
        List<Object> rawMessages = redisTemplate.opsForList().range(key, start, end);
        return rawMessages != null ? rawMessages.stream().map(Object::toString).toList() : List.of();
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
    public void deleteMessage(Long specId, String message) {
        String key = "spec:" + specId;
        redisTemplate.opsForList().remove(key, 1, message);
    }
}

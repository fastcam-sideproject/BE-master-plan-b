package com.example.masterplanbbe.chat.repository;

import com.example.masterplanbbe.chat.ChatLog;
import com.example.masterplanbbe.chat.ChatMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
public class RedisChatRepository {

    @Qualifier
    private final RedisTemplate<String, Object> redisTemplate;
    private final ChatLogRepository chatLogRepository;
    private static final int MAX_MESSAGES = 3;
    private final ObjectMapper objectMapper;

    @Autowired
    public RedisChatRepository(@Qualifier("chatTemplate") RedisTemplate<String, Object> redisTemplate, ChatLogRepository chatLogRepository, @Qualifier("objectMapper") ObjectMapper objectMapper) {
        this.chatLogRepository = chatLogRepository;
        this.redisTemplate = redisTemplate;
        redisTemplate.setKeySerializer(new StringRedisSerializer()); // 키 한글 깨짐 방지
        redisTemplate.setValueSerializer(new StringRedisSerializer()); // 값 한글 깨짐 방지
        this.objectMapper = objectMapper;
    }

    public void saveMessage(String examId, ChatMessage message) {
        String key = "chat:" + examId;
        try {
            String jsonMessage = objectMapper.writeValueAsString(message);
            redisTemplate.opsForList().leftPush(key, jsonMessage);

            // 메시지 개수 초과하면 MySQL에 저장
            if (redisTemplate.opsForList().size(key) > MAX_MESSAGES) {
                List<Object> oldMessages = redisTemplate.opsForList().range(key, MAX_MESSAGES, -1);
                if (oldMessages != null) {
                    for (Object obj : oldMessages) {
                        ChatMessage oldMessage = objectMapper.readValue(obj.toString(), ChatMessage.class);
                        ChatLog chatLog = new ChatLog(oldMessage.getExamId(), oldMessage.getMemberId(), oldMessage.getContent(), oldMessage.getSendTime());
                        chatLogRepository.save(chatLog);
                    }
                }
                redisTemplate.opsForList().trim(key, 0, MAX_MESSAGES - 1); // 오래된 메시지 삭제
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public List<Object> getRecentMessages(Long examId) {
        String key = "chat:" + examId;
        return redisTemplate.opsForList().range(key, 0, -1);
    }
}

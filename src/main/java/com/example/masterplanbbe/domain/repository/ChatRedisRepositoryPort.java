package com.example.masterplanbbe.domain.repository;

import com.example.masterplanbbe.application.dto.ChatRedisDto;

import java.util.List;

public interface ChatRedisRepositoryPort {

    // 채팅 메시지를 Redis에 저장
    void saveMessage(Long specId, ChatRedisDto chatRedisDto);

    // 특정 채팅방의 메시지 개수 조회
    Long getMessageCount(Long specId);

    // 특정 범위의 채팅 메시지를 조회
    List<ChatRedisDto> getMessagesInRange(Long specId, int start, int end);

    // MySQL로 이동된 메시지 삭제
    void trimMessages(Long specId, int maxMessages);

    // 저장된 모든 채팅방 리스트 조회 (배치 서비스에서 사용)
    List<String> getAllChatRooms();

    // 특정 채팅 메시지를 삭제
    Long deleteMessage(Long specId, ChatRedisDto chatRedisDto);
}

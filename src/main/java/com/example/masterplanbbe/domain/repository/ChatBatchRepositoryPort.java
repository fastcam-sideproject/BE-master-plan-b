package com.example.masterplanbbe.domain.repository;

import com.example.masterplanbbe.domain.entity.ChatMessage;

import java.util.List;

public interface ChatBatchRepositoryPort {
    // 배치로 채팅 메시지 저장
    void saveAll(List<ChatMessage> messages);
}

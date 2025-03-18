package com.example.masterplanbbe.domain.repository;

import com.example.masterplanbbe.presentation.response.ChatResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface ChatMessageRepositoryCustom {
    Slice<ChatResponse> findChatList(Long lastChatId, Long specId, Pageable pageable);
}

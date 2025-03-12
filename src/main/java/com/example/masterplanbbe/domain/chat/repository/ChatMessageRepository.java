package com.example.masterplanbbe.domain.chat.repository;

import com.example.masterplanbbe.domain.chat.ChatMessage;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    @Query("""
                SELECT cm FROM ChatMessage cm
                WHERE cm.specId = :specId
                AND cm.id < :lastChatId
                ORDER BY cm.id DESC
            """)
    Slice<ChatMessage> findChatList(Long lastChatId, Long specId, Pageable pageable);
}

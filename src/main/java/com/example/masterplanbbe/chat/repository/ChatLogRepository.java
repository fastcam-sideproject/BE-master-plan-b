package com.example.masterplanbbe.chat.repository;

import com.example.masterplanbbe.chat.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatLogRepository extends JpaRepository<ChatMessage, Long> {
}

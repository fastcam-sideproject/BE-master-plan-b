package com.example.masterplanbbe.chat.repository;

import com.example.masterplanbbe.chat.ChatLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatLogRepository extends JpaRepository<ChatLog, Long> {
}
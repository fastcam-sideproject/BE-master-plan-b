package com.example.masterplanbbe.domain.repository;

import com.example.masterplanbbe.domain.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long>, ChatMessageRepositoryCustom {
}
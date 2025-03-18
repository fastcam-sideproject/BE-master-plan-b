package com.example.masterplanbbe.domain.fixture;

import com.example.masterplanbbe.domain.entity.ChatMessage;
import com.example.masterplanbbe.domain.entity.Member;

import java.time.LocalDateTime;

public class ChatMessageFixture {
    public static ChatMessage createMessage(Long id, Long parentId, String content, Member member) {
        return ChatMessage.builder()
                .id(id)
                .specId(1L)
                .memberId(member.getId())
                .content(content)
                .sendAt(LocalDateTime.now())
                .parentId(parentId)
                .member(member)
                .build();
    }
}
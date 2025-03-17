package com.example.masterplanbbe.presentation.response;

import com.example.masterplanbbe.application.dto.ChatRedisDto;
import com.example.masterplanbbe.presentation.request.ChatRequest;

import java.time.LocalDateTime;

public record ChatResponse(
        Long id,
        Long specId,
        Long parentId,
        Long memberId,
        String nickname,
        String content,
        LocalDateTime sendAt,
        String parentContent,
        String parentNickname
) {
    public static ChatResponse from(ChatRedisDto chatRedisDto) {
        return new ChatResponse(
                chatRedisDto.id(),
                chatRedisDto.specId(),
                chatRedisDto.parentId(),
                chatRedisDto.memberId(),
                chatRedisDto.nickname(),
                chatRedisDto.content(),
                chatRedisDto.sendAt(),
                chatRedisDto.parentContent(),
                chatRedisDto.parentNickname()
        );
    }
}

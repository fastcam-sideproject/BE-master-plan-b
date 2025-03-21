package com.example.masterplanbbe.presentation.request;

import java.time.LocalDateTime;

public record ChatRequest(
        Long specId,
        Long parentId,
        Long memberId,
        String nickname,
        String content,
        LocalDateTime sendAt,
        String parentContent,
        String parentNickname
) {}

package com.example.masterplanbbe.presentation.response;

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
) {}

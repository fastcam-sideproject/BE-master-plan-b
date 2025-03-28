package com.example.masterplanbbe.presentation.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public record ChatRequest(
        @NotNull @Positive Long specId,
        Long parentId,
        @NotNull @Positive Long memberId,
        @NotBlank String nickname,
        @NotBlank String content,
        @NotNull LocalDateTime sendAt,
        String parentContent,
        String parentNickname
) {}

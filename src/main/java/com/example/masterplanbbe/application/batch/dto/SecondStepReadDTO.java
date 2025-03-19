package com.example.masterplanbbe.application.batch.dto;

public record SecondStepReadDTO(
        Long examId,
        Long specId,
        Integer totalLikeCount,
        Integer totalViewCount) {
}

package com.example.masterplanbbe.application.batch.dto;

public record FinalJoinReadDTO(
        String ageGroup,
        Double score,
        Long specId,
        String jobRoleName,
        Long categoryId
) {
}
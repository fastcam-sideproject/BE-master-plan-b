package com.example.masterplanbbe.application.batch.dto;

public record FinalJoinReadDTO(
        String ageGroup,
        Double score,
        Long specId,
        Long jobRoleId,
        Long categoryId
) {
}
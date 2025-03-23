package com.example.masterplanbbe.application.batch.dto;

public record JoinJobRoleReadDTO(
        String ageGroup,
        Double score,
        Long specId,
        String jobRoleName,
        Long categoryId
) {
}
package com.example.masterplanbbe.application.batch.dto;

import com.example.masterplanbbe.domain.enums.AgeGroup;

public record FinalCalculationWriteDTO(
        AgeGroup ageGroup,
        Double score,
        Long specId,
        Long jobRoleId,
        Long categoryId
) {
}

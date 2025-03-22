package com.example.masterplanbbe.application.batch.dto;

import java.time.LocalDate;

public record PreCalculationDTO(
        Long examId,
        Long specId,
        String specName,
        LocalDate applyEndDate,
        LocalDate examStartDate,
        Integer participantCount,
        Integer totalLikeCount,
        Integer totalViewCount) {
}

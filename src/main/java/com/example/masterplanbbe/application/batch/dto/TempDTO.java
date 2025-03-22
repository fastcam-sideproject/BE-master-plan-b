package com.example.masterplanbbe.application.batch.dto;

import java.time.LocalDate;

public record TempDTO(
        Long examId,
        Long specId,
        LocalDate applyEndDate,
        LocalDate examStartDate,
        Integer participantCount,
        Integer totalLikeCount,
        Integer totalViewCount) {
}

package com.example.masterplanbbe.application.batch.dto;

import java.time.LocalDate;

public record FirstStepReadDTO(
        Long specId,
        Long examId,
        LocalDate applyEndDate,
        LocalDate examStartDate,
        Integer participantCount,
        Integer likeCount,
        Integer viewCount) {
}

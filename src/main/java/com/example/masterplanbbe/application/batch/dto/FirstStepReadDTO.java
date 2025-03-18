package com.example.masterplanbbe.application.batch.dto;

import java.time.LocalDate;

public record FirstStepReadDTO(
        Long examId,
        LocalDate applyEndDate,
        LocalDate examStartDate,
        Integer participantCount) {
}

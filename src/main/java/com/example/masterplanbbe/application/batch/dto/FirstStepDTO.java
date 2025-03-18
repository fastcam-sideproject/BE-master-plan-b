package com.example.masterplanbbe.application.batch.dto;

import java.time.LocalDate;

public record FirstStepDTO(
        Long examId,
        LocalDate applyEndDate,
        LocalDate examStartDate,
        Integer participantCount) {
}

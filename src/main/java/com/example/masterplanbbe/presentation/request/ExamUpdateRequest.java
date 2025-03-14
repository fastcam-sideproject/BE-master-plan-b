package com.example.masterplanbbe.presentation.request;

import com.example.masterplanbbe.domain.entity.Exam;

import java.time.LocalDate;

public record ExamUpdateRequest(
        String name,
        Integer participantCount,
        LocalDate applyStartDate,
        LocalDate applyEndDate,
        LocalDate examStartDate
) {
    public void update(Exam exam) {
        exam.update(
                name,
                participantCount,
                applyStartDate,
                applyEndDate,
                examStartDate
        );
    }
}

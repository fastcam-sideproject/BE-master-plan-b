package com.example.masterplanbbe.domain.exam.request;

import com.example.masterplanbbe.domain.exam.entity.Exam;

import java.time.LocalDate;

public record ExamUpdateRequest(
        String name,
        Double difficulty,
        Integer participantCount,
        LocalDate applyStartDate,
        LocalDate applyEndDate,
        LocalDate examStartDate
) {
    public void update(Exam exam) {
        exam.update(
                name,
                difficulty,
                participantCount,
                applyStartDate,
                applyEndDate,
                examStartDate
        );
    }
}

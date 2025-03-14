package com.example.masterplanbbe.domain.exam.response;

import com.example.masterplanbbe.domain.exam.entity.Exam;

import java.time.LocalDate;

public record UpdateExamResponse(
        String name,
        Integer participantCount,
        LocalDate applyStartDate,
        LocalDate applyEndDate,
        LocalDate examStartDate
) {
    public UpdateExamResponse(Exam exam) {
        this(
                exam.getName(),
                exam.getParticipantCount(),
                exam.getApplyStartDate(),
                exam.getApplyEndDate(),
                exam.getExamStartDate()
        );
    }
}

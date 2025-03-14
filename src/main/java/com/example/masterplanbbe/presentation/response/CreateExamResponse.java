package com.example.masterplanbbe.presentation.response;

import com.example.masterplanbbe.domain.entity.Exam;

import java.time.LocalDate;

public record CreateExamResponse(
        Long examId,
        String name,
        Integer participantCount,
        LocalDate applyStartDate,
        LocalDate applyEndDate,
        LocalDate examStartDate
) {
    public CreateExamResponse(Exam exam) {
        this(
                exam.getId(),
                exam.getName(),
                exam.getParticipantCount(),
                exam.getApplyStartDate(),
                exam.getApplyEndDate(),
                exam.getExamStartDate()
        );
    }
}

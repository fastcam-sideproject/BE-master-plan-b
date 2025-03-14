package com.example.masterplanbbe.domain.exam.response;

import com.example.masterplanbbe.domain.exam.entity.Exam;

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

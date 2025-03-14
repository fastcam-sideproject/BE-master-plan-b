package com.example.masterplanbbe.domain.exam.request;

import com.example.masterplanbbe.domain.exam.entity.Exam;
import com.example.masterplanbbe.domain.exam.entity.ExamDetail;

import java.time.LocalDate;

public record ExamCreateRequest(
        String name,
        Integer participantCount,
        LocalDate applyStartDate,
        LocalDate applyEndDate,
        LocalDate examStartDate,
        ExamDetail examDetail
) {
    public Exam toEntity() {
        return new Exam(
                examDetail,
                name,
                participantCount,
                applyStartDate,
                applyEndDate,
                examStartDate
        );
    }
}

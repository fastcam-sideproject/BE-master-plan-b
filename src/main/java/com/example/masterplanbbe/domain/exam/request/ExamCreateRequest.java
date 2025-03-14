package com.example.masterplanbbe.domain.exam.request;

import com.example.masterplanbbe.domain.exam.entity.Exam;
import com.example.masterplanbbe.domain.exam.entity.ExamDetail;
import jakarta.persistence.EntityManager;

import java.time.LocalDate;

public record ExamCreateRequest(
        String name,
        Integer participantCount,
        LocalDate applyStartDate,
        LocalDate applyEndDate,
        LocalDate examStartDate,
        Long examDetailId
) {
    public Exam toEntity(EntityManager entityManager) {
        ExamDetail examDetail = entityManager.getReference(ExamDetail.class, examDetailId);
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

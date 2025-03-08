package com.example.masterplanbbe.domain.exam.dto;

import com.example.masterplanbbe.domain.exam.entity.Exam;
import com.example.masterplanbbe.domain.exam.enums.Category;
import com.example.masterplanbbe.domain.spec.entity.Spec;
import com.querydsl.core.annotations.QueryProjection;

import java.time.LocalDate;

public @QueryProjection record ExamItemCardDto (
        String name,
        Double difficulty,
        Category category,
        LocalDate applyStartDate,
        LocalDate examStartDate,
        Boolean isBookmarked
) {
    @QueryProjection
    public ExamItemCardDto(Exam exam, Spec spec, Boolean isBookmarked) {
        this(exam.getName(), exam.getDifficulty(), spec.getCategory(), exam.getApplyStartDate(), exam.getExamStartDate(), isBookmarked);
    }
}

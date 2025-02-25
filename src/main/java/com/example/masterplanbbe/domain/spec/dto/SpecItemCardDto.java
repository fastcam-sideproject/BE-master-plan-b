package com.example.masterplanbbe.domain.spec.dto;

import com.example.masterplanbbe.domain.exam.entity.Exam;
import com.example.masterplanbbe.domain.exam.enums.Category;
import com.example.masterplanbbe.domain.spec.entity.Spec;
import com.querydsl.core.annotations.QueryProjection;

import java.time.LocalDate;

public record SpecItemCardDto(
        String name,
        Category category,
        Double difficulty,
        Integer participants,
        Boolean isBookmarked,
        LocalDate applyStartDate,
        LocalDate applyEndDate,
        LocalDate examStartDate
) {
    @QueryProjection
    public SpecItemCardDto(Spec spec, Exam exam, Boolean isBookmarked) {
        this(spec.getName(), spec.getCategory(), exam.getDifficulty(), exam.getParticipantCount(), isBookmarked, exam.getApplyStartDate(), exam.getApplyEndDate(), exam.getExamStartDate());
    }
}

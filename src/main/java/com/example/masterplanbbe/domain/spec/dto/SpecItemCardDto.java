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
        LocalDate applyStartDate,
        LocalDate applyEndDate,
        LocalDate examStartDate,
        Boolean isBookmarked
) {
    @QueryProjection
    public SpecItemCardDto(Spec spec,
                           Exam exam,
                           Boolean isBookmarked) {
        this(
                spec.getName(),
                spec.getCategory(),
                exam != null ? exam.getDifficulty() : null,
                exam != null ? exam.getParticipantCount() : null,
                exam != null ? exam.getApplyStartDate() : null,
                exam != null ? exam.getApplyEndDate() : null,
                exam != null ? exam.getExamStartDate() : null,
                isBookmarked
        );
    }
}

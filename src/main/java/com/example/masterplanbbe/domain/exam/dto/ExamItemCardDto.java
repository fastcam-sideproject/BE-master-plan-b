package com.example.masterplanbbe.domain.exam.dto;

import com.example.masterplanbbe.domain.exam.enums.Category;
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
}

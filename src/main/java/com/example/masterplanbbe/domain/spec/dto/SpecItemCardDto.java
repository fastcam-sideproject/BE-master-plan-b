package com.example.masterplanbbe.domain.spec.dto;

import com.example.masterplanbbe.domain.exam.enums.Category;
import com.querydsl.core.annotations.QueryProjection;

import java.time.LocalDate;

public @QueryProjection record SpecItemCardDto (
    String name,
    Category category,
    Integer participants,
    LocalDate applyStartDate,
    LocalDate applyEndDate,
    LocalDate examStartDate,
    Boolean isBookmarked
) {
}

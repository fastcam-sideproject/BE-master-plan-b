package com.example.masterplanbbe.application.dto;

import com.example.masterplanbbe.domain.enums.SpecCategory;
import com.querydsl.core.annotations.QueryProjection;

import java.time.LocalDate;

public @QueryProjection record SpecItemCardDto (
    String name,
    SpecCategory specCategory,
    Integer participants,
    LocalDate applyStartDate,
    LocalDate applyEndDate,
    LocalDate examStartDate,
    Boolean isBookmarked
) {
}

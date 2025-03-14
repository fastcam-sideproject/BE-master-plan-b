package com.example.masterplanbbe.application.dto;

import com.example.masterplanbbe.domain.enums.SpecCategory;
import com.querydsl.core.annotations.QueryProjection;

import java.time.LocalDate;

public @QueryProjection record ExamItemCardDto (
        String name,
        SpecCategory specCategory,
        LocalDate applyStartDate,
        LocalDate examStartDate,
        Boolean isBookmarked
) {
}

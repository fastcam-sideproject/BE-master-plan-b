package com.example.masterplanbbe.domain.spec.dto;

import com.example.masterplanbbe.domain.exam.enums.Category;

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
}

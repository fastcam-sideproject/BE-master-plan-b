package com.example.masterplanbbe.domain.spec.dto;

import com.example.masterplanbbe.domain.exam.enums.Category;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@Getter
public class SpecItemCardDto {
    private final String name;
    private final Category category;
    private final Double difficulty;
    private final Integer participants;
    private final LocalDate applyStartDate;
    private final LocalDate applyEndDate;
    private final LocalDate examStartDate;
    private final Boolean isBookmarked;

    @QueryProjection
    public SpecItemCardDto(
            String name,
            Category category,
            Double difficulty,
            Integer participants,
            LocalDate applyStartDate,
            LocalDate applyEndDate,
            LocalDate examStartDate,
            Boolean isBookmarked
    ) {
        this.name = name;
        this.category = category;
        this.difficulty = difficulty;
        this.participants = participants;
        this.applyStartDate = applyStartDate;
        this.applyEndDate = applyEndDate;
        this.examStartDate = examStartDate;
        this.isBookmarked = isBookmarked;
    }
}

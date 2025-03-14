package com.example.masterplanbbe.domain.enums;

import com.example.masterplanbbe.infrastructure.sort.enums.SortOption;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ExamSortOption implements SortOption {
    START_DATE("examStartDate");

    private final String fieldName;
    private static final String ALIAS = "exam";

    @Override
    public String getSortField() {
        return fieldName;
    }

    @Override
    public String getEntityAlias() {
        return ALIAS;
    }
}

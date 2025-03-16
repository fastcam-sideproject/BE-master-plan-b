package com.example.masterplanbbe.domain.enums;

import com.example.masterplanbbe.infrastructure.sort.enums.SortOption;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum SpecSortOption implements SortOption {
    SCRAP_COUNT("scrapCount"),
    REMAINING_DAYS("remainingDays"),
    RECOMMENDED("recommended");

    private final String fieldName;
    private static final String ALIAS = "spec";

    @Override
    public String getSortField() {
        return fieldName;
    }

    @Override
    public String getEntityAlias() {
        return ALIAS;
    }
}

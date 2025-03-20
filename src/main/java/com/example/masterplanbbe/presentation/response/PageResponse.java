package com.example.masterplanbbe.presentation.response;

import com.example.masterplanbbe.infrastructure.sort.page.CustomPage;

import java.util.List;

public record PageResponse<T>(
        int page,
        int size,
        long totalElements,
        List<T> content,
        boolean hasNext
) {
    public PageResponse(CustomPage<T> customPage) {
        this(customPage.page(), customPage.size(), customPage.totalElements(), customPage.content(), customPage.hasNext());
    }
}

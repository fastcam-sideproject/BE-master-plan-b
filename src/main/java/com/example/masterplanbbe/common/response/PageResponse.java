package com.example.masterplanbbe.common.response;

import com.example.masterplanbbe.common.page.CustomPage;

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

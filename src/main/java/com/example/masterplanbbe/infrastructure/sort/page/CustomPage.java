package com.example.masterplanbbe.infrastructure.sort.page;

import java.util.List;

public record CustomPage<T>(
        int page,
        int size,
        long totalElements,
        List<T> content
) {

    public boolean hasNext() {
        return getOffset() + content.size() < totalElements;
    }

    private int getOffset() {
        return page * size;
    }

}

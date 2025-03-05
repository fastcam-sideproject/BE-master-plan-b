package com.example.masterplanbbe.common.response;

import java.util.List;

public record CustomPageResponse<T>(
        int page,
        int size,
        long totalElements,
        List<T> content
) {

    public int getTotalPages() {
        return (int) Math.ceil((double) totalElements / size);
    }

    public int getOffset() {
        return page * size;
    }

    public boolean hasPrevious() {
        return page > 0;
    }

    public boolean hasNext() {
        return getOffset() + content.size() < totalElements;
    }
}

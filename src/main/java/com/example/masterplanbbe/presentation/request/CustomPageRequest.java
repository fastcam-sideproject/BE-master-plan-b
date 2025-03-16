package com.example.masterplanbbe.presentation.request;

import com.example.masterplanbbe.infrastructure.sort.enums.SortOption;

public record CustomPageRequest<T extends SortOption> (
        Integer page,
        Integer size,
        T sort,
        Boolean isAsc
) {
    public Long getOffset() {
        return (long) page * size;
    }
}

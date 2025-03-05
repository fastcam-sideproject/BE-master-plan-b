package com.example.masterplanbbe.common.request;

import com.example.masterplanbbe.common.enums.SortOption;

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

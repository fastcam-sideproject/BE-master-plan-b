package com.example.masterplanbbe.common.request;

import com.example.masterplanbbe.common.enums.SortOption;

public record CustomPageRequest (
        Integer page,
        Integer size,
        SortOption sort,
        Boolean isAsc
) {
    public Long getOffset() {
        return (long) page * size;
    }
}

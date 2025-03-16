package com.example.masterplanbbe.presentation.response;

import com.example.masterplanbbe.domain.entity.SpecBookmark;

public record CreateSpecBookmarkResponse(
        Long examBookmarkId,
        Long specId,
        Long memberId
) {
    public CreateSpecBookmarkResponse(SpecBookmark specBookmark) {
        this(
                specBookmark.getId(),
                specBookmark.getSpec().getId(),
                specBookmark.getMember().getId()
        );
    }
}

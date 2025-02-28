package com.example.masterplanbbe.domain.specBookmark.response;

import com.example.masterplanbbe.domain.specBookmark.entity.SpecBookmark;

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

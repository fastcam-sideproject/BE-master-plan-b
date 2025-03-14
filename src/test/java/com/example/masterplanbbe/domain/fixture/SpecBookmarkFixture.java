package com.example.masterplanbbe.domain.fixture;

import com.example.masterplanbbe.domain.entity.Member;
import com.example.masterplanbbe.domain.entity.Spec;
import com.example.masterplanbbe.domain.entity.SpecBookmark;
import com.example.masterplanbbe.utils.TestUtils;

public class SpecBookmarkFixture {
    public static SpecBookmark createSpecBookmark(Member member,
                                                  Spec spec) {
        return SpecBookmark.builder()
                .member(member)
                .spec(spec)
                .build();
    }

    public static SpecBookmark createExistingSpecBookmark(Member member,
                                                          Spec spec) {
        return TestUtils.createExistingEntity(
                () -> createSpecBookmark(member, spec)
        );
    }
}

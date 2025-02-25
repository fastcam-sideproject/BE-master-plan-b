package com.example.masterplanbbe.domain.fixture;

import com.example.masterplanbbe.domain.spec.entity.Spec;
import com.example.masterplanbbe.domain.specBookmark.entity.SpecBookmark;
import com.example.masterplanbbe.member.entity.Member;
import com.example.masterplanbbe.utils.TestUtils;

public class SpecBookmarkFixture {
    public static SpecBookmark createExistingSpecBookmarkOf(Long memberId, Long specId) {
        Member member = MemberFixture.createExistingMemberFrom(memberId);
        Spec spec = SpecFixture.createExistingSpecFrom(specId);

        return TestUtils.createExistingEntity(() -> SpecBookmark.builder()
                .member(member)
                .spec(spec)
                .build());
    }
}

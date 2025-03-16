package com.example.masterplanbbe.domain.fixture;

import com.example.masterplanbbe.presentation.request.MemberCreateRequestDTO;
import com.example.masterplanbbe.domain.entity.Member;
import com.example.masterplanbbe.utils.TestUtils;

import static com.example.masterplanbbe.domain.enums.MemberRoleEnum.*;

public class MemberFixture {
    public static Member createMember() {
        MemberCreateRequestDTO requestDTO = new MemberCreateRequestDTO(
                "test@test.com",
                "nickname",
                "password",
                false
        );
        return new Member(
                requestDTO,
                "password",
                USER
        );
    }

    public static Member createExistingMember() {
        return TestUtils.createExistingEntity(MemberFixture::createMember);
    }

    public static Member createExistingMemberFrom(Long memberId) {
        return TestUtils.createExistingEntity(MemberFixture::createMember, memberId);
    }
}

package com.example.masterplanbbe.domain.fixture;

import com.example.masterplanbbe.member.entity.Member;
import com.example.masterplanbbe.utils.TestUtils;

import java.time.LocalDate;

public class MemberFixture {
    public static Member createMember() {
        return Member.builder()
                .userId("test")
                .email("test@test.com")
                .name("name")
                .nickname("nickname")
                .password("password")
                .phoneNumber("010-1234-5678")
                .birthday(LocalDate.parse("1999-01-01"))
                .profileImageUrl("profileImageUrl")
                .build();
    }

    public static Member creatfExistingMember() {
        return TestUtils.createExistingEntity(MemberFixture::createMember);
    }
}

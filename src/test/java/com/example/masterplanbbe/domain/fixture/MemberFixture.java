package com.example.masterplanbbe.domain.fixture;

import com.example.masterplanbbe.domain.member.dto.MemberCreateRequestDTO;
import com.example.masterplanbbe.domain.member.entity.Member;
import com.example.masterplanbbe.domain.member.entity.MemberRoleEnum;
import com.example.masterplanbbe.utils.TestUtils;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;

import static com.example.masterplanbbe.domain.member.entity.MemberRoleEnum.*;
import static com.example.masterplanbbe.domain.member.entity.QMember.member;

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

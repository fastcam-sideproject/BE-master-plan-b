package com.example.masterplanbbe.common.security.dto;

import com.example.masterplanbbe.domain.member.entity.Member;
import com.example.masterplanbbe.domain.member.entity.MemberRoleEnum;

public record MemberInfoDTO(String email, String nickname, MemberRoleEnum role) {
    public MemberInfoDTO(Member member) {
        this(member.getEmail(), member.getNickname(), member.getRole());
    }
}

package com.example.masterplanbbe.infrastructure.security.dto;

import com.example.masterplanbbe.domain.entity.Member;
import com.example.masterplanbbe.domain.entity.MemberRoleEnum;

public record MemberInfoDTO(String email, String nickname, MemberRoleEnum role) {
    public MemberInfoDTO(Member member) {
        this(member.getEmail(), member.getNickname(), member.getRole());
    }
}

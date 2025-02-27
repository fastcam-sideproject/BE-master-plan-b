package com.example.masterplanbbe.common.security.dto;

import com.example.masterplanbbe.domain.member.entity.Member;

public record MemberInfoDTO(String userId, String email, String name, String nickname) {
    public MemberInfoDTO(Member member) {
        this(member.getUserId(), member.getEmail(), member.getName(), member.getNickname());
    }
}

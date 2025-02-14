package com.example.masterplanbbe.security.dto;

import com.example.masterplanbbe.member.entity.Member;

public record MemberInfoDTO(String userId, String email, String name, String nickname) {
    public MemberInfoDTO(Member member) {
        this(member.getUserId(), member.getEmail(), member.getName(), member.getNickname());
    }
}

package com.example.masterplanbbe.domain.member.dto;

import com.example.masterplanbbe.domain.member.entity.Member;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class MemberResponse {
    private String userId;
    private String email;
    private String nickname;

    public MemberResponse(Member member) {
        this.userId = member.getUserId();
        this.email = member.getEmail();
        this.nickname = member.getNickname();
    }
}

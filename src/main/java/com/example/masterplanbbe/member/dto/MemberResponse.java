package com.example.masterplanbbe.member.dto;

import com.example.masterplanbbe.member.entity.Member;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

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

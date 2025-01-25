package com.example.masterplanbbe.member.service.response;

import com.example.masterplanbbe.member.entity.Member;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@ToString
public class MemberResponse {
    private Long id;
    private String userId;
    private String email;
    private String name;
    private String nickname;
    private String password;
    private String phoneNumber;
    private LocalDate birthday;
    private String profileImageUrl;
    private String role;
    private LocalDateTime createTime;
    private LocalDateTime modifiedTime;

    public static MemberResponse from(Member member) {
        MemberResponse response = new MemberResponse();
        response.id = member.getId();
        response.userId = member.getUserId();
        response.email = member.getEmail();
        response.name = member.getName();
        response.nickname = member.getNickname();
        response.password = member.getPassword();
        response.phoneNumber = member.getPhoneNumber();
        response.profileImageUrl = member.getProfileImageUrl();
        response.role = member.getRole();
        response.createTime = member.getCreateTime();
        response.modifiedTime = member.getModifiedTime();
        return response;


    }
}

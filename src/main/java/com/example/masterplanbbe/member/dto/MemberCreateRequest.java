package com.example.masterplanbbe.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@ToString
@AllArgsConstructor
public class MemberCreateRequest {
    private String userId;
    private String email;
    private String name;
    private String nickname;
    private String password;
}

package com.example.masterplanbbe.security.dto;

import com.example.masterplanbbe.member.entity.MemberRoleEnum;

public record OAuth2UserDTO(
        String userId,
        String email,
        String nickname,
        String profileImage,
        MemberRoleEnum role
) {}
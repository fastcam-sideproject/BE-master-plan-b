package com.example.masterplanbbe.common.security.dto;

import com.example.masterplanbbe.domain.member.entity.MemberRoleEnum;

public record OAuth2UserDTO(
        String userId,
        String email,
        String nickname,
        String profileImage,
        MemberRoleEnum role
) {}
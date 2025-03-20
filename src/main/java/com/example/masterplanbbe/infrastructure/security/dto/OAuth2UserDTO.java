package com.example.masterplanbbe.infrastructure.security.dto;

import com.example.masterplanbbe.domain.enums.MemberRoleEnum;

public record OAuth2UserDTO(
        String userId,
        String email,
        String nickname,
        String profileImage,
        MemberRoleEnum role
) {}
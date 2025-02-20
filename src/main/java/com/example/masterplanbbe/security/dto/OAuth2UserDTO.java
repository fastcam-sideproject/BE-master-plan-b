package com.example.masterplanbbe.security.dto;

public record OAuth2UserDTO(
        String userId,
        String email,
        String nickname,
        String profileImage,
        String role
) {}
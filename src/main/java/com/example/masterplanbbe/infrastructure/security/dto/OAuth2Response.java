package com.example.masterplanbbe.infrastructure.security.dto;

public interface OAuth2Response {
    String getProvider();
    String getProviderId();
    String getEmail();
    String getNickname();
    String getProfileImage();
}

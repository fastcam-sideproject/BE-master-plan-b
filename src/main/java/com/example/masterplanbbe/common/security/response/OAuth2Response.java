package com.example.masterplanbbe.common.security.response;

public interface OAuth2Response {
    String getProvider();
    String getProviderId();
    String getEmail();
    String getNickname();
    String getProfileImage();
}

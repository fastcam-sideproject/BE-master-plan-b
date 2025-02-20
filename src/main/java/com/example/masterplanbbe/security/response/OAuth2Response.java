package com.example.masterplanbbe.security.response;

public interface OAuth2Response {
    String getProvider();
    String getProviderId();
    String getEmail();
    String getNickname();
    String getProfileImage();
}

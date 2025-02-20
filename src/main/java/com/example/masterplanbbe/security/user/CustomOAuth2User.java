package com.example.masterplanbbe.security.user;

import com.example.masterplanbbe.security.dto.OAuth2UserDTO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public class CustomOAuth2User implements OAuth2User {

    private final OAuth2UserDTO userDTO;

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(userDTO::role);
        return authorities;
    }

    @Override
    public String getName() {
        return userDTO.nickname();
    }

    public String getProfileImage() {
        return userDTO.profileImage();
    }

    public String getUserId() {
        return userDTO.userId();
    }

    public String getEmail() {
        return userDTO.email();
    }
}

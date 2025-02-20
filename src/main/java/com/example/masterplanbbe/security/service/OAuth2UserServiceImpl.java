package com.example.masterplanbbe.security.service;

import com.example.masterplanbbe.member.entity.Member;
import com.example.masterplanbbe.member.entity.MemberRoleEnum;
import com.example.masterplanbbe.member.repository.MemberRepository;
import com.example.masterplanbbe.security.dto.OAuth2UserDTO;
import com.example.masterplanbbe.security.response.GoogleResponse;
import com.example.masterplanbbe.security.response.KakaoResponse;
import com.example.masterplanbbe.security.response.NaverResponse;
import com.example.masterplanbbe.security.response.OAuth2Response;
import com.example.masterplanbbe.security.user.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuth2UserServiceImpl extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(userRequest);
        String oauthClientName = userRequest.getClientRegistration().getClientName();

        System.out.println("클라이언트 이름 : " + oauthClientName);

        OAuth2Response response;

        if (oauthClientName.equalsIgnoreCase("google"))
            response = new GoogleResponse(oauth2User.getAttributes());
        else if (oauthClientName.equalsIgnoreCase("kakao"))
            response = new KakaoResponse(oauth2User.getAttributes());
        else if (oauthClientName.equalsIgnoreCase("naver"))
            response = new NaverResponse(oauth2User.getAttributes());
        else throw new OAuth2AuthenticationException("Invalid client registration");

        System.out.println("제공자: " + response.getProvider());
        System.out.println("제공자 ID: " + response.getProviderId());
        System.out.println("사용자 이메일: " + response.getEmail());
        System.out.println("사용자 닉네임: " + response.getNickname());
        System.out.println("사용자 프로필 이미지: " + response.getProfileImage());

        String id = response.getProvider() + response.getProviderId() + response.getEmail();
        OAuth2UserDTO dto = new OAuth2UserDTO(
                id, response.getEmail(), response.getNickname(), response.getProfileImage(), MemberRoleEnum.USER
        );

        Optional<Member> memberOptional = memberRepository.findByEmail(response.getEmail());
        UserDetailsImpl userDetails;

        if (memberOptional.isPresent()) {
            // 로그인 처리
            Member member = memberOptional.get();

            // OAuth 2.0 로그인과 커스텀 로그인 중복 방지
            if (!member.getIsOAuth2()) throw new IllegalArgumentException("이미 커스텀 가입되어 있는 계정");

            userDetails = new UserDetailsImpl(member, oauth2User.getAttributes());
        } else {
            // 회원가입 처리
            Member member = Member.create(dto);
            memberRepository.save(member);

            userDetails = new UserDetailsImpl(member, oauth2User.getAttributes());
        }

        return userDetails;
    }
}

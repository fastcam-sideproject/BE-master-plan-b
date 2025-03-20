package com.example.masterplanbbe.domain.member;

import com.example.masterplanbbe.infrastructure.configuration.SecurityConfig;
import com.example.masterplanbbe.infrastructure.exception.JwtAccessDenyHandler;
import com.example.masterplanbbe.infrastructure.exception.JwtAuthenticationEntryPoint;
import com.example.masterplanbbe.infrastructure.security.filter.CustomLoginFilter;
import com.example.masterplanbbe.infrastructure.security.handler.CustomLogoutHandler;
import com.example.masterplanbbe.infrastructure.security.handler.OAuth2FailureHandler;
import com.example.masterplanbbe.infrastructure.security.handler.OAuth2SuccessHandler;
import com.example.masterplanbbe.infrastructure.security.jwt.JwtService;
import com.example.masterplanbbe.infrastructure.security.user.UserDetailsImpl;
import com.example.masterplanbbe.presentation.controller.MemberController;
import com.example.masterplanbbe.domain.entity.Member;
import com.example.masterplanbbe.domain.enums.MemberRoleEnum;
import com.example.masterplanbbe.domain.repository.MemberRepository;
import com.example.masterplanbbe.domain.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@Import(SecurityConfig.class)
@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(MemberController.class)
public class MemberSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserDetailsService userDetailsService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private AuthenticationConfiguration authenticationConfiguration;

    @MockBean
    private CustomLoginFilter customLoginFilter;

    @MockBean
    private CustomLogoutHandler customLogoutHandler;

    @MockBean
    private JwtAccessDenyHandler jwtAccessDenyHandler;

    @MockBean
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @MockBean
    private DefaultOAuth2UserService oAuth2UserService;

    @MockBean
    private OAuth2SuccessHandler oAuth2SuccessHandler;

    @MockBean
    private OAuth2FailureHandler oAuth2FailureHandler;

    @MockBean
    private MemberService memberService;

    @MockBean
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {}

//    @Test
//    @WithAnonymousUser(setupBefore = TestExecutionEvent.TEST_EXECUTION)
//    void test1() throws Exception {
//        mockMvc.perform(get("/api/v1/member/security")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().is4xxClientError());
//
//        // 테스트 환경의 스프링 시큐리티는 엔트리 포인트가 동작하지 않고 리다이렉션 기본 동작...?
//    }

    @Test
    @DisplayName("커스텀 스프링 시큐리티 설정 테스트 환경 통일 처리 인증 테스트")
    @WithMockUser(username = "testUser", roles = {"USER", "ADMIN"})
    void test() throws Exception {
        String mockToken = "token";
        Member member = mock(Member.class);
        when(member.getEmail()).thenReturn("testUser");
        when(member.getRole()).thenReturn(MemberRoleEnum.USER);

        UserDetails userDetails = new UserDetailsImpl(member);

        when(userDetailsService.loadUserByUsername("testUser")).thenReturn(userDetails);

        when(jwtService.validateAccessToken(mockToken))
                .thenReturn(new JwtService.MemberPayload("testUser", mockToken));
        when(jwtService.getRoleFromAccessToken(mockToken))
                .thenReturn(MemberRoleEnum.USER);

        mockMvc.perform(get("/api/v1/member/security")
                        .header("Authorization", mockToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}

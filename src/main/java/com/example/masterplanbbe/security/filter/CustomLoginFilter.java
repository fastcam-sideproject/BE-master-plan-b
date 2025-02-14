package com.example.masterplanbbe.security.filter;

import com.example.masterplanbbe.common.exception.ErrorCode;
import com.example.masterplanbbe.common.response.ApiResponse;
import com.example.masterplanbbe.common.response.ErrorResponse;
import com.example.masterplanbbe.member.entity.Member;
import com.example.masterplanbbe.member.entity.MemberRoleEnum;
import com.example.masterplanbbe.security.dto.LoginDTO;
import com.example.masterplanbbe.security.dto.MemberInfoDTO;
import com.example.masterplanbbe.security.jwt.JwtService;
import com.example.masterplanbbe.security.user.UserDetailsImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j(topic = "CustomLoginFilter")
public class CustomLoginFilter extends UsernamePasswordAuthenticationFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    private final JwtService jwtService;

    public CustomLoginFilter(JwtService jwtService) {
        this.jwtService = jwtService;
        setFilterProcessesUrl("/api/v1/member/login");
        super.setUsernameParameter("userId");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("로그인 단계 진입");

        try {
            LoginDTO requestDto = new ObjectMapper().readValue(request.getInputStream(), LoginDTO.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestDto.userId(),
                            requestDto.password(),
                            null));
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new InternalAuthenticationServiceException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.info("로그인 성공 및 JWT 생성");

        String userId = ((UserDetailsImpl) authResult.getPrincipal()).getUsername();
        MemberRoleEnum role = ((UserDetailsImpl) authResult.getPrincipal()).getMember().getRole();
        Date date = new Date();

        // 응답 헤더 엑세스 토큰 추가
        String accessToken = jwtService.createToken(userId, role, date);
        response.addHeader(AUTHORIZATION_HEADER, URLEncoder.encode(accessToken, StandardCharsets.UTF_8).replaceAll("\\+", "%20"));

        Member member = ((UserDetailsImpl) authResult.getPrincipal()).getMember();
        MemberInfoDTO dto = new MemberInfoDTO(member);
        ApiResponse<MemberInfoDTO> apiResponse = ApiResponse.ok(dto);

        sendResponseMsg(response, HttpStatus.OK.value(), apiResponse);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String errorMessage;
        if (exception instanceof BadCredentialsException) {
            errorMessage = "아이디와 비밀번호를 확인해주세요.";
        } else if (exception instanceof InternalAuthenticationServiceException) {
            errorMessage = "내부 시스템 문제로 로그인할 수 없습니다. 관리자에게 문의하세요.";
        } else if (exception instanceof UsernameNotFoundException) {
            errorMessage = "존재하지 않는 계정입니다.";
        } else {
            errorMessage = "알 수 없는 오류입니다.";
        }

        log.error("로그인 실패 :{}", errorMessage);
        ErrorResponse<String> errorResponse = ErrorResponse.of(ErrorCode.LOGIN_FAIL, errorMessage);
        sendResponseMsg(response, HttpStatus.UNAUTHORIZED.value(), errorResponse);
    }

    private void sendResponseMsg(HttpServletResponse response, int statusCode, Object responseBody) throws IOException {
        response.setStatus(statusCode);
        response.setContentType("application/json;charset=UTF-8");
        try (PrintWriter writer = response.getWriter()) {
            writer.print(new ObjectMapper().writeValueAsString(responseBody));
            writer.flush();
        } catch(IOException e){
            log.error(e.getMessage());
        }
    }
}

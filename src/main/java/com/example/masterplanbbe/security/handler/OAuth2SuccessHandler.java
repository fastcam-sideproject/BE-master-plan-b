package com.example.masterplanbbe.security.handler;

import com.example.masterplanbbe.common.response.ApiResponse;
import com.example.masterplanbbe.member.entity.Member;
import com.example.masterplanbbe.member.entity.MemberRoleEnum;
import com.example.masterplanbbe.security.dto.MemberInfoDTO;
import com.example.masterplanbbe.security.jwt.JwtService;
import com.example.masterplanbbe.security.user.UserDetailsImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    private final JwtService jwtService;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        log.info("OAuth 2.0 로그인 성공");

        String userId = ((UserDetailsImpl) authentication.getPrincipal()).getUsername();
        MemberRoleEnum role = ((UserDetailsImpl) authentication.getPrincipal()).getMember().getRole();
        Date date = new Date();

        log.info("OAuth 2.0 로그인 ID: {}", userId);
        log.info("OAuth 2.0 로그인 권한: {}", role.getRole());

        String accessToken = jwtService.createToken(userId, role, date);
        response.addHeader(AUTHORIZATION_HEADER, URLEncoder.encode(accessToken, StandardCharsets.UTF_8)
                .replaceAll("\\+", "%20"));

        Member member = ((UserDetailsImpl) authentication.getPrincipal()).getMember();
        MemberInfoDTO dto = new MemberInfoDTO(member);
        ApiResponse<MemberInfoDTO> apiResponse = ApiResponse.ok("OAuth 2.0 로그인에 성공하였습니다.", dto);

        sendResponseMsg(response, HttpStatus.OK.value(), apiResponse);
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

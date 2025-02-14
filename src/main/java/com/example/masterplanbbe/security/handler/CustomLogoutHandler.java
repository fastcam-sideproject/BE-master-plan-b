package com.example.masterplanbbe.security.handler;

import com.example.masterplanbbe.common.exception.ErrorCode;
import com.example.masterplanbbe.common.response.ApiResponse;
import com.example.masterplanbbe.common.response.ErrorResponse;
import com.example.masterplanbbe.security.jwt.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomLogoutHandler implements LogoutHandler {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    private final JwtService jwtService;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        log.info("로그아웃 핸들러 작동");
        String tokenValue = request.getHeader(AUTHORIZATION_HEADER);

        if (tokenValue == null || tokenValue.isEmpty()) return;

        // 헤더 엑세스 토큰 추출 및 검증
        String decodedToken = URLDecoder.decode(tokenValue, StandardCharsets.UTF_8);
        JwtService.MemberPayload memberPayload = jwtService.validateAccessToken(decodedToken);

        // ** 프론트 측에서 헤더에 엑세스토큰을 보내준 후, 자체적으로 삭제 작업 필요 **

        try {
            jwtService.removeRefreshToken(memberPayload.getMember().getUserId());
            ApiResponse<String> apiResponse = ApiResponse.ok("로그아웃에 성공하였습니다.");
            sendResponseMsg(response, 200, apiResponse);
        } catch (Exception e) {
            log.error("로그아웃 과정 중 오류 발생", e);
            ErrorResponse<String> errorResponse = ErrorResponse.of(ErrorCode.LOGOUT_EXCEPTION);
            try {
                sendResponseMsg(response, 500, errorResponse);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
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

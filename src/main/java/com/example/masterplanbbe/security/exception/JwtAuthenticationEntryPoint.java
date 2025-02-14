package com.example.masterplanbbe.security.exception;

import com.example.masterplanbbe.common.exception.ErrorCode;
import com.example.masterplanbbe.common.response.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * 비인증 사용자 접근 시도 방지
 */
@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
        log.info("401 예외 발생: {}", request.getRequestURI());

        ErrorResponse<String> errorResponse = ErrorResponse.of(ErrorCode.UNAUTHORIZED_ACCESS, authException.getMessage());
        sendResponseMsg(response, errorResponse);
    }

    private void sendResponseMsg(HttpServletResponse response, Object responseBody) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");
        try (PrintWriter writer = response.getWriter()) {
            writer.print(new ObjectMapper().writeValueAsString(responseBody));
            writer.flush();
        } catch(IOException e){
            log.error(e.getMessage());
        }
    }
}

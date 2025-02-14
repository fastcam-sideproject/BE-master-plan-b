package com.example.masterplanbbe.security.exception;

import com.example.masterplanbbe.common.exception.ErrorCode;
import com.example.masterplanbbe.common.response.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * 비인가(권한 부족) 사용자 접근 시도 방지
 */
@Slf4j
@Component
public class JwtAccessDenyHandler implements AccessDeniedHandler {

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException) throws ServletException, IOException {
        log.info("403 예외 발생: {}", request.getRequestURI());

        ErrorResponse<String> errorResponse = ErrorResponse.of(ErrorCode.FORBIDDEN_ACCESS, accessDeniedException.getMessage());
        sendResponseMsg(response, errorResponse);
    }

    private void sendResponseMsg(HttpServletResponse response, Object responseBody) throws IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json;charset=UTF-8");
        try (PrintWriter writer = response.getWriter()) {
            writer.print(new ObjectMapper().writeValueAsString(responseBody));
            writer.flush();
        } catch(IOException e){
            log.error(e.getMessage());
        }
    }
}

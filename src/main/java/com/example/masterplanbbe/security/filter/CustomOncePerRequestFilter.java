package com.example.masterplanbbe.security.filter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.List;

public abstract class CustomOncePerRequestFilter extends OncePerRequestFilter {

    private static final List<String> passableUris = List.of(
            // Base Endpoint
            "/api/v1/member/create",
            "/api/v1/member/login",
            "/api/v1/member/test",

            // Swagger
            "/swagger",
            "/v3/api-docs",

//            // OAuth 2.0
            "/oauth2",
//
////            // temp about OAuth 2.0
////            "/login",
            "/favicon.ico",
            "/error" // 얘를 열어줘야 favicon 에러가 안 찍히는데... 시그니처랑은 상관이 없나?
    );

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String uri = request.getRequestURI();

        for (String passableUri : passableUris) {
            if (uri.startsWith(passableUri)) {
                return true;
            }
        }

        return false;
    }
}

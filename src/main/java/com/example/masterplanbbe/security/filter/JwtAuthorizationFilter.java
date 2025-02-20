package com.example.masterplanbbe.security.filter;

import com.example.masterplanbbe.common.exception.ErrorCode;
import com.example.masterplanbbe.member.entity.MemberRoleEnum;
import com.example.masterplanbbe.security.exception.CustomAuthenticationException;
import com.example.masterplanbbe.security.jwt.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Slf4j(topic = "JwtAuthorizationFilter")
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends CustomOncePerRequestFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // 권한 별로 어떻게 할 건지 향후 고민
        String tokenValue = request.getHeader(AUTHORIZATION_HEADER);
        // 여기서의 커스텀 예외: 헤더 엑세스 토큰 제거 + 예외 반환
        /**
         * 여기도 마찬가지
         */
        if (tokenValue == null || tokenValue.isEmpty())
            throw new CustomAuthenticationException(ErrorCode.WRONG_TOKEN_ISSUE, "헤더 엑세스 토큰 없음");

        String decodedToken = URLDecoder.decode(tokenValue, StandardCharsets.UTF_8);
        MemberRoleEnum role = jwtService.getRoleFromAccessToken(decodedToken);

        log.info("요청 URI: {}", request.getRequestURI());
        log.info("요청 권한: {}", role.getRole());

        filterChain.doFilter(request, response);
    }
}

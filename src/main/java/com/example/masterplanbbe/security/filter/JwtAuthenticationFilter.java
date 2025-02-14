package com.example.masterplanbbe.security.filter;

import com.example.masterplanbbe.member.entity.Member;
import com.example.masterplanbbe.security.jwt.JwtService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
@Slf4j(topic = "JwtAuthenticationFilter")
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("요청 URI : {}", request.getRequestURI());

        String tokenValue = request.getHeader(AUTHORIZATION_HEADER);
        // 여기서의 커스텀 예외: 헤더 엑세스 토큰 제거 + 예외 반환
        if (tokenValue == null || tokenValue.isEmpty()) throw new JwtException("엑세스 토큰 없음");

        // 헤더 엑세스 토큰 추출 및 검증
        String decodedToken = URLDecoder.decode(tokenValue, StandardCharsets.UTF_8);
        JwtService.MemberPayload memberPayload = jwtService.validateAccessToken(decodedToken);

        Member member = memberPayload.getMember();
        String accessToken = memberPayload.getAccessToken();
        accessToken = URLEncoder.encode(accessToken, StandardCharsets.UTF_8).replaceAll("\\+", "%20");

        // 응답 헤더에 엑세스 토큰 인코딩 값 추가
        response.addHeader(AUTHORIZATION_HEADER, accessToken);

        // 인증 객체 세팅
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(createAuthentication(member.getUserId()));
        SecurityContextHolder.setContext(context);

        // 다음 필터 넘기기
        filterChain.doFilter(request, response);
    }

    private Authentication createAuthentication(String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}

package com.example.masterplanbbe.security.jwt;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class TokenUtils {

    private static final String BEARER_PREFIX = "Bearer ";
    private static final String AUTHORIZATION_KEY = "auth";

    private final SecretKey secretKey;

    public TokenUtils(
            @Value("${jwt.secret.key}") String secret) {
        this.secretKey = new SecretKeySpec(
                secret.getBytes(StandardCharsets.UTF_8),
                Jwts.SIG.HS256.key().build().getAlgorithm());
    }

    public String createToken(TokenPayload tokenPayload) {
        return BEARER_PREFIX +
                Jwts.builder()
                        .subject(tokenPayload.getSub()) // 사용자 식별값
                        .claim(AUTHORIZATION_KEY, tokenPayload.getRole()) // 사용자 권한
                        .expiration(tokenPayload.getExpiresAt()) // 만료 시간
                        .issuedAt(tokenPayload.getIat()) // 발급일
                        .id(tokenPayload.getJti()) // 토큰 ID
                        .signWith(secretKey, Jwts.SIG.HS256) // 암호화 키
                        .compact();
    }

    // 유효 엑세스 토큰에서의 userId, role 검증
    public String getUserIdFromAccessToken(String token) throws JwtException {
        token = getSubstringToken(token);
        return getClaimsFromToken(token).getSubject();
    }

    public String getRoleFromAccessToken(String token) throws JwtException {
        token = getSubstringToken(token);
        return getClaimsFromToken(token).get(AUTHORIZATION_KEY, String.class);
    }

    private Claims getClaimsFromToken(String token) throws JwtException {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // 기간 만료 엑세스 토큰에서의 userId, role 검출
    public String getUserIdFromExpiredAccessToken(ExpiredJwtException e) {
        return e.getClaims().getSubject();
    }

    public String getRoleFromExpiredAccessToken(ExpiredJwtException e) {
        return e.getClaims().get(AUTHORIZATION_KEY, String.class);
    }

    // 리프레시 토큰 유효성 검증
    public boolean parseRefreshToken(String token) {
        log.info("리프레시 토큰 검증 진입");

        try {
            Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
            log.info("유효한 리프레시 토큰");
            return true;
        } catch (JwtException e) {
            log.error("리프레시 토큰 검증 예외: {}", e.getMessage());
            return false;
        }
    }

    // 토큰 접두어 제거
    private static String getSubstringToken(String token) {
        return token.startsWith(BEARER_PREFIX) ? token.substring(BEARER_PREFIX.length()) : token;
    }
}

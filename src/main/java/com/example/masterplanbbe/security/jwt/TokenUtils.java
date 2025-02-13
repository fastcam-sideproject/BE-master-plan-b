package com.example.masterplanbbe.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

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

    public String getUserIdFromAccessToken(String token) throws JwtException {
        token = token.substring(BEARER_PREFIX.length());

        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public String getUserIdFromExpiredAccessToken(ExpiredJwtException e) {
        return e.getClaims().getSubject();
    }

    public boolean parseRefreshToken(String token) {
        token = token.substring(BEARER_PREFIX.length());

        try {
            Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }
}

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

    public Claims parseAccessToken(String token) {
        token = token.substring(BEARER_PREFIX.length());

        try {
            return Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (MalformedJwtException | SignatureException | UnsupportedJwtException | IllegalArgumentException e) {
            throw new JwtException("유효하지 않은 토큰입니다.");
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
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

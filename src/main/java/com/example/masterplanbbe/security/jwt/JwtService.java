package com.example.masterplanbbe.security.jwt;

import com.example.masterplanbbe.member.entity.MemberRoleEnum;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class JwtService {

    private static final String REDIS_AUTH_KEY = "AUTH_";
    private static final String BEARER_PREFIX = "Bearer ";
    private final long ACCESS_TOKEN_EAT = 60 * 60 * 1000L; // 1H(테스트 2분)
    private final long REFRESH_TOKEN_EAT = 7 * 24 * 60 * 60 * 1000L; // 7D

    private final TokenUtils tokenUtils;
    private final RedisTemplate<String, String> authTemplate;

    public JwtService(
            TokenUtils tokenUtils,
            @Qualifier("authTemplate") RedisTemplate<String, String> authTemplate) {
        this.tokenUtils = tokenUtils;
        this.authTemplate = authTemplate;
    }

    /**
     * 1. 엑세스 토큰 검증
     * 1-1. 유효하면 그대로 통과
     * 2. 엑세스 토큰이 유효하지 않음
     * 2-1. 기간 만료 이외의 사유로 유효하지 않으면 예외 반환
     * 2-2. 위의 단계 통과 후, 기간 만료라면 서브젝트(유저 아이디) 추출
     * 3. 유저 아이디를 기반으로 리프레시 토큰 검증
     * 3-1. 기간 만료 이외의 사유로 유효하지 않으면 예외 반환(강제 로그아웃)
     * 3-2. 유효한 리프레시 토큰이면 엑세스 토큰 재발급
     * 4. 처리(리스폰스 헤더 포함 등) 후, 통과
     */
    public MemberPayload validateAccessToken(String tokenValue) {
        String userId, accessToken;
        MemberRoleEnum role;

        try {
            userId = tokenUtils.getUserIdFromAccessToken(tokenValue);
            role = getRoleFromRoleString(tokenUtils.getRoleFromAccessToken(tokenValue));
            accessToken = tokenValue;
        } catch (ExpiredJwtException e) {
            userId = tokenUtils.getUserIdFromExpiredAccessToken(e);
            role = getRoleFromRoleString(tokenUtils.getRoleFromExpiredAccessToken(e));

            // Refresh Token 검증 필요
            String refreshToken = authTemplate.opsForValue().get(REDIS_AUTH_KEY + userId);

            // 여기서의 커스텀 예외: 강제 로그아웃 + 헤더 엑세스 토큰 제거 + 레디스 리프레시 토큰 제거 + 예외 반환
            if (refreshToken == null) throw new JwtException("No Refresh Token");
            if (!tokenUtils.parseRefreshToken(refreshToken)) throw new JwtException("Invalid Refresh Token");

            Date date = new Date();
            accessToken = tokenUtils.createToken(
                    new TokenPayload(userId, UUID.randomUUID().toString(), date, new Date(date.getTime() + ACCESS_TOKEN_EAT), role));
        } catch (JwtException e) {
            // 여기서의 커스텀 예외: 헤더 엑세스 토큰 제거 + 예외 반환
            throw new JwtException("Invalid JWT Access Token");
        }

        return new MemberPayload(userId, accessToken);
    }

    // 인가 필터에서의 활용을 위한 별도 메소드
    public MemberRoleEnum getRoleFromAccessToken(String tokenValue) {
        try {
            String roleString = tokenUtils.getRoleFromAccessToken(tokenValue);
            return getRoleFromRoleString(roleString);
        } catch (ExpiredJwtException e) {
            String roleString = tokenUtils.getRoleFromExpiredAccessToken(e);
            return getRoleFromRoleString(roleString);
        } catch (JwtException e) {
            // 여기서의 커스텀 예외: 리프레시 토큰 제거 + 강제 로그아웃 + 예외 반환
            throw new JwtException("Invalid JWT Refresh Token");
        }
    }

    private MemberRoleEnum getRoleFromRoleString(String roleString) {
        if (roleString.equals("ROLE_USER")) {
            return MemberRoleEnum.USER;
        } else if (roleString.equals("ROLE_ADMIN")) {
            return MemberRoleEnum.ADMIN;
        }

        // 여기서의 커스텀 예외: 적합한 역할 없음 + 예외 반환
        throw new JwtException("Invalid Role String");
    }

    @Getter
    @AllArgsConstructor
    public static class MemberPayload {
        private String userId;
        private String accessToken;
    }

    /**
     * 토큰 생성
     */
    public String createToken(String userId, MemberRoleEnum role, Date date) {
        TokenPayload accessTokenPayload = new TokenPayload(
                userId, UUID.randomUUID().toString(), date, new Date(date.getTime() + ACCESS_TOKEN_EAT), role);
        TokenPayload refreshTokenPayload = new TokenPayload(
                userId, UUID.randomUUID().toString(), date, new Date(date.getTime() + REFRESH_TOKEN_EAT), role);

        String refreshToken = tokenUtils.createToken(refreshTokenPayload).substring(BEARER_PREFIX.length());
        authTemplate.opsForValue().set(REDIS_AUTH_KEY + userId, refreshToken);

        return tokenUtils.createToken(accessTokenPayload);
    }

    /**
     * 로그아웃 - 리프레시 토큰 삭제
     */
    public void removeRefreshToken(String userId) {
        authTemplate.delete(REDIS_AUTH_KEY + userId);
    }
}

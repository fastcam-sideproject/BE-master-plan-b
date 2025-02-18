package com.example.masterplanbbe.security.jwt;

import com.example.masterplanbbe.common.exception.ErrorCode;
import com.example.masterplanbbe.member.entity.MemberRoleEnum;
import com.example.masterplanbbe.security.exception.CustomAuthenticationException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class JwtService {

    private static final String REDIS_AUTH_KEY = "AUTH_";
    private static final String BEARER_PREFIX = "Bearer ";
    private static final String BLACKLIST_ACCESS_TOKEN = "BLACKLIST_ACCESS_TOKEN:";
    private static final String BLACKLIST_REFRESH_TOKEN = "BLACKLIST_REFRESH_TOKEN:";
    private static final long ACCESS_TOKEN_EAT = 60 * 60 * 1000L; // 1H(테스트 2분)
    private static final long REFRESH_TOKEN_EAT = 7 * 24 * 60 * 60 * 1000L; // 7D

    private final TokenUtils tokenUtils;
    private final RedisTemplate<String, String> authTemplate;
    private final RedisTemplate<String, String> blacklistTokenTemplate;

    public JwtService(
            TokenUtils tokenUtils,
            @Qualifier("authTemplate") RedisTemplate<String, String> authTemplate,
            @Qualifier("blacklistTokenTemplate") RedisTemplate<String, String>
                    blacklistTokenTemplate) {
        this.tokenUtils = tokenUtils;
        this.authTemplate = authTemplate;
        this.blacklistTokenTemplate = blacklistTokenTemplate;
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

        // 외부 공격자 검증
        if (Boolean.TRUE.equals(blacklistTokenTemplate.hasKey(
                BLACKLIST_ACCESS_TOKEN + tokenValue.substring(BEARER_PREFIX.length())))) {
            log.error("토큰 하이재킹 이슈 발생! 블랙리스트 토큰 기반 인증 시도 감지!");
            throw new CustomAuthenticationException(ErrorCode.TOKEN_HIJACKING_ISSUE, ErrorCode.TOKEN_HIJACKING_ISSUE.getMessage());
        }

        try {
            userId = tokenUtils.getUserIdFromAccessToken(tokenValue);
            role = getRoleFromRoleString(tokenUtils.getRoleFromAccessToken(tokenValue));
            accessToken = tokenValue;
        } catch (ExpiredJwtException e) {
            // 무효가 된 엑세스 토큰 블랙리스트 추가
            blacklistTokenTemplate.opsForValue()
                    .set(
                            BLACKLIST_ACCESS_TOKEN + tokenValue.substring(BEARER_PREFIX.length()),
                            tokenValue.substring(BEARER_PREFIX.length()),
                            ACCESS_TOKEN_EAT,
                            TimeUnit.MILLISECONDS);

            userId = tokenUtils.getUserIdFromExpiredAccessToken(e);
            role = getRoleFromRoleString(tokenUtils.getRoleFromExpiredAccessToken(e));

            // Refresh Token 검증 필요
            String refreshToken = authTemplate.opsForValue().get(REDIS_AUTH_KEY + userId);

            // 여기서의 커스텀 예외: 강제 로그아웃 + 헤더 엑세스 토큰 제거 + 레디스 리프레시 토큰 제거 + 예외 반환
            if (refreshToken == null)
                throw new CustomAuthenticationException(ErrorCode.WRONG_TOKEN_ISSUE, "리프레시 토큰 없음. 헤더 엑세스 토큰 제거 필요.");
            if (!tokenUtils.parseRefreshToken(refreshToken)) {
                blacklistTokenTemplate.opsForValue()
                        .set(
                                BLACKLIST_REFRESH_TOKEN + refreshToken,
                                refreshToken, REFRESH_TOKEN_EAT, TimeUnit.MILLISECONDS);
                authTemplate.delete(REDIS_AUTH_KEY + userId);
                throw new CustomAuthenticationException(ErrorCode.WRONG_TOKEN_ISSUE, "유효하지 않은 리프레시 토큰. 헤더 엑세스 토큰 제거 필요.");
            }

            Date date = new Date();
            TokenPayload accessTokenPayload = new TokenPayload(
                    userId, UUID.randomUUID().toString(), date, new Date(date.getTime() + ACCESS_TOKEN_EAT), role);
            accessToken = tokenUtils.createToken(accessTokenPayload);

            // 5번 시도 후 유효한 토큰을 찾을 때까지 반복
            for (int i = 0; i < 5; i++) {
                // 블랙리스트에 포함된 토큰이 아니면 종료
                if (Objects.equals(blacklistTokenTemplate
                                .opsForValue()
                                .get(BLACKLIST_ACCESS_TOKEN + accessToken.substring(BEARER_PREFIX.length())),
                                accessToken.substring(BEARER_PREFIX.length()))) break;

                accessToken = tokenUtils.createToken(accessTokenPayload);
            }
        } catch (JwtException e) {
            // 여기서의 커스텀 예외: 헤더 엑세스 토큰 제거 + 예외 반환
            throw new CustomAuthenticationException(ErrorCode.WRONG_TOKEN_ISSUE, "비정상적인 헤더 엑세스 토큰. 헤더 엑세스 토큰 제거 필요.");
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
            throw new CustomAuthenticationException(ErrorCode.WRONG_TOKEN_ISSUE, "비정상적인 헤더 엑세스 토큰. 헤더 엑세스 토큰 제거 필요.");
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

        // 5번 시도 후, 유효한 토큰을 찾을 때까지 반복
        for (int i = 0; i < 5; i++) {
            // 블랙리스트에 포함된 토큰이 아니면 종료
            if (Objects.equals(blacklistTokenTemplate
                    .opsForValue().get(BLACKLIST_REFRESH_TOKEN + refreshToken), refreshToken)) break;

            refreshToken = tokenUtils.createToken(refreshTokenPayload).substring(BEARER_PREFIX.length());
        }

        authTemplate.opsForValue().set(REDIS_AUTH_KEY + userId, refreshToken, 7, TimeUnit.DAYS);

        String newAccessToken = tokenUtils.createToken(accessTokenPayload);

        // 5번 시도 후 유효한 토큰을 찾을 때까지 반복
        for (int i = 0; i < 5; i++) {
            // 블랙리스트에 포함된 토큰이 아니면 종료
            if (Objects.equals(blacklistTokenTemplate
                            .opsForValue()
                            .get(BLACKLIST_ACCESS_TOKEN + newAccessToken.substring(BEARER_PREFIX.length())),
                    newAccessToken.substring(BEARER_PREFIX.length()))) break;

            newAccessToken = tokenUtils.createToken(accessTokenPayload);
        }

        return newAccessToken;
    }

    /**
     * 로그아웃 - 리프레시 토큰 삭제 + 블랙리스트 추가
     */
    public void removeRefreshToken(String userId) {
        String refreshToken = authTemplate.opsForValue().get(REDIS_AUTH_KEY + userId);

        if (refreshToken == null) {
            throw new CustomAuthenticationException(ErrorCode.INTERNAL_SERVER_ERROR, "리프레시 토큰 탈취 가능성!");
        }

        blacklistTokenTemplate.opsForValue()
                .set(
                        BLACKLIST_REFRESH_TOKEN + refreshToken,
                        refreshToken, REFRESH_TOKEN_EAT, TimeUnit.MILLISECONDS);
        authTemplate.delete(REDIS_AUTH_KEY + userId);
    }

    public void addBlacklistAccessToken(String tokenValue) {
        blacklistTokenTemplate.opsForValue()
                .set(
                        BLACKLIST_ACCESS_TOKEN + tokenValue.substring(BEARER_PREFIX.length()),
                        tokenValue.substring(BEARER_PREFIX.length()),
                        ACCESS_TOKEN_EAT,
                        TimeUnit.MILLISECONDS);
    }
}

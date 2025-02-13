package com.example.masterplanbbe.security.jwt;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class JwtService {

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
}

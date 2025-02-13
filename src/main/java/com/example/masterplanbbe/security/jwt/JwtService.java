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


}

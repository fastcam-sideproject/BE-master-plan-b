package com.example.masterplanbbe.infrastructure.exception;

import lombok.Getter;
import org.springframework.security.core.AuthenticationException;

@Getter
public class CustomAuthenticationException extends AuthenticationException {
    private final String message;

    public CustomAuthenticationException(ErrorCode errorCode, String message) {
        super(errorCode.getMessage());
        this.message = message;
    }
}

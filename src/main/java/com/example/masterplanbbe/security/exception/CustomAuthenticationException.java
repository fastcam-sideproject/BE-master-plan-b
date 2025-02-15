package com.example.masterplanbbe.security.exception;

import com.example.masterplanbbe.common.exception.ErrorCode;
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

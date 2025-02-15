package com.example.masterplanbbe.security.exception;

import com.example.masterplanbbe.common.exception.ErrorCode;
import org.springframework.security.core.AuthenticationException;

public class CustomAuthenticationException extends AuthenticationException {
    public CustomAuthenticationException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public CustomAuthenticationException(String msg) {
        super(msg);
    }

    public CustomAuthenticationException(ErrorCode errorCode) {
        super(errorCode.getMessage());
    }
}

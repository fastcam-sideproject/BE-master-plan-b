package com.example.masterplanbbe.infrastructure.exception;

import lombok.Getter;

@Getter
public class DuplicateUserException extends RuntimeException {

    private final ErrorCode errorCode;

    public DuplicateUserException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}

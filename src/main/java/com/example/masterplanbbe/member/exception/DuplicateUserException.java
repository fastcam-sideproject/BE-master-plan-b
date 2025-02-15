package com.example.masterplanbbe.member.exception;

import com.example.masterplanbbe.common.exception.ErrorCode;
import lombok.Getter;

@Getter
public class DuplicateUserException extends RuntimeException {

    private final ErrorCode errorCode;

    public DuplicateUserException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}

package com.example.masterplanbbe.member.exception;

import com.example.masterplanbbe.common.exception.BaseException;
import com.example.masterplanbbe.common.exception.ErrorCode;

public class DuplicateUserException extends BaseException {
    protected DuplicateUserException(ErrorCode errorCode) {
        super(errorCode);
    }
}

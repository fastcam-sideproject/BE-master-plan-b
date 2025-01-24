package com.example.masterplanbbe.domain.post.exception;

import com.example.masterplanbbe.common.exception.BaseException;
import com.example.masterplanbbe.common.exception.ErrorCode;

public class PostException extends BaseException {

    public PostException(ErrorCode errorCode) {
        super(errorCode);
    }
}

package com.example.masterplanbbe.common.response;

import com.example.masterplanbbe.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ErrorResponse<T> {
    private final int status;
    private final String message;
    private final T data;

    public static <T> ErrorResponse<T> of(ErrorCode errorCode) {
        return new ErrorResponse<>(errorCode.getStatus(), errorCode.getMessage(), null);
    }

    public static <T> ErrorResponse<T> of(ErrorCode errorCode, T data) {
        return new ErrorResponse<>(errorCode.getStatus(), errorCode.getMessage(), data);
    }
}

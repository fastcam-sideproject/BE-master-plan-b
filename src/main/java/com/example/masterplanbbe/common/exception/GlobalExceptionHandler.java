package com.example.masterplanbbe.common.exception;

import com.example.masterplanbbe.common.response.ErrorResponse;
import com.example.masterplanbbe.member.exception.DuplicateUserException;
import jakarta.annotation.Priority;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Priority(Integer.MAX_VALUE)
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(GlobalException.NotFoundException.class)
    public ErrorResponse<?> handleNotFoundException(GlobalException.NotFoundException e) {
        return ErrorResponse.of(e.getErrorCode());
    }

    @ExceptionHandler(GlobalException.InternalServerException.class)
    public ErrorResponse<?> handleInternalServerException(GlobalException.InternalServerException e) {
        return ErrorResponse.of(e.getErrorCode());
    }

    @ExceptionHandler(GlobalException.IllegalStateException.class)
    public ErrorResponse<?> handleIllegalStateException(GlobalException.IllegalStateException e) {
        return ErrorResponse.of(e.getErrorCode());
    }

    @ExceptionHandler(GlobalException.BadRequestException.class)
    public ErrorResponse<?> handleBadRequestException(GlobalException.BadRequestException e) {
        return ErrorResponse.of(e.getErrorCode());
    }

    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<ErrorResponse<?>> handleDuplicateUserException(DuplicateUserException e) {
        return ResponseEntity.status(e.getErrorCode().getStatus()).body(ErrorResponse.of(e.getErrorCode()));
    }
}

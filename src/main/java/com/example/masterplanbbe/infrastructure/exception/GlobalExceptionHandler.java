package com.example.masterplanbbe.infrastructure.exception;

import com.example.masterplanbbe.presentation.response.ErrorResponse;
import jakarta.annotation.Priority;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Priority(Integer.MAX_VALUE)
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BaseException.class)
    public ErrorResponse<?> handleBaseException(BaseException e) {
        return ErrorResponse.of(e.getErrorCode());
    }

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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse<?> handleValidationErrors(MethodArgumentNotValidException e) {
        Map<String, List<String>> fieldErrors = new HashMap<>();

        e.getBindingResult()
                .getFieldErrors()
                .forEach(er -> fieldErrors
                            .computeIfAbsent(er.getField(), k -> new ArrayList<>())
                            .add(er.getDefaultMessage()));

        return ErrorResponse.of(
                HttpStatus.BAD_REQUEST.value(),
                "유효성 검증을 통과하지 못했습니다.",
                fieldErrors);
    }
}

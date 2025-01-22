package com.example.masterplanbbe.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ApiResponse<T> {
    private int status;
    private String message;
    private T data;

    public static <T> ApiResponse<T> ok() {
        return new ApiResponse<>(200, "OK", null);
    }

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(200, "OK", data);
    }
}

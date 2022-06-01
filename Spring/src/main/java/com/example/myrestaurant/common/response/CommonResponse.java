package com.example.myrestaurant.common.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CommonResponse <T>{
    private T data;
    private String message;
    private Result result;

    public static <T> CommonResponse<T> success(T data, String message) {
        return new CommonResponse<T>(data, message, Result.SUCCESS);
    }

    public static CommonResponse fail(String message) {
        return CommonResponse.builder()
                .result(Result.FAIL)
                .message(message)
                .build();
    }

    public enum Result {
        SUCCESS,
        FAIL
    }
}

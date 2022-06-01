package com.example.myrestaurant.common.exception;

public class UserTokenNotFoundException extends RuntimeException{
    private static final String MESSAGE = "사용자 토큰이 잘못됐습니다. 재 로그인이 필요합니다.";

    public UserTokenNotFoundException() {
        super(MESSAGE);
    }
}

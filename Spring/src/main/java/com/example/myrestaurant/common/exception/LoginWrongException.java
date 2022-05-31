package com.example.myrestaurant.common.exception;

public class LoginWrongException extends RuntimeException{
    private static final String MESSAGE = "아이디나 비밀번호가 잘못됐습니다.";

    public LoginWrongException() {
        super(MESSAGE);
    }
}

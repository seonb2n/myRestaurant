package com.example.myrestaurant.common.exception;

public class AlreadyExistEmailException extends RuntimeException{
    private static final String MESSAGE = "이미 등록된 이메일입니다.";

    public AlreadyExistEmailException() {
        super(MESSAGE);
    }
}

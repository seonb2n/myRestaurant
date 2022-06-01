package com.example.myrestaurant.common.exception;

public class RestaurantNameNotFoundException extends RuntimeException{
    private static final String MESSAGE = "레스토랑 이름이 잘못됐습니다.";

    public RestaurantNameNotFoundException() {
        super(MESSAGE);
    }
}

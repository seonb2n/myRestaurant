package com.example.myrestaurant.common.exception;

public class RestaurantTokenNotFoundException extends RuntimeException{
    private static final String MESSAGE = "레스토랑 토큰이 잘못됐습니다.";

    public RestaurantTokenNotFoundException() {
        super(MESSAGE);
    }
}

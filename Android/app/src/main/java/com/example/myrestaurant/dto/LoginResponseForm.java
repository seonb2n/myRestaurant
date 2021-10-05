package com.example.myrestaurant.dto;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

public class LoginResponseForm {

    @SerializedName("status")
    private String responseStatus;

    @SerializedName("message")
    private String responseMessage;

    @SerializedName("nickName")
    private String userName;

    @SerializedName("userId")
    private Long userId;

    @SerializedName("restaurantList")
    private Restaurant[] restaurants;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Restaurant[] getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(Restaurant[] restaurants) {
        this.restaurants = restaurants;
    }

    public String getResponseStatus() {
        return responseStatus;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    @Override
    public String toString() {
        return "LoginResponseForm{" +
                "userName='" + userName + '\'' +
                ", userId=" + userId +
                ", restaurants=" + Arrays.toString(restaurants) +
                '}';
    }
}

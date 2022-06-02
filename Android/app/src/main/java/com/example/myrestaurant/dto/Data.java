package com.example.myrestaurant.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {

    @SerializedName("userToken")
    private String userToken;

    @SerializedName("nickName")
    private String nickName;

    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    @SerializedName("restaurantList")
    private List<Restaurant> restaurantList;

}

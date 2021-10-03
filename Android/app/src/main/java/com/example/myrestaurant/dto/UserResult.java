package com.example.myrestaurant.dto;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class UserResult {

    @SerializedName("userId")
    private Long userId;

    @SerializedName("email")
    private String email;

    @SerializedName("nickName")
    private String name;

    @Override
    public String toString() {
        return ">>> Result : userId = " + userId + " email= "+email +" name= "+ name;
    }

}

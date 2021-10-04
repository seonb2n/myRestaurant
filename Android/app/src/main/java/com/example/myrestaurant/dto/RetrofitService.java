package com.example.myrestaurant.dto;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetrofitService {

    @GET("/getUserData")
    Call<UserResult> getUser();

    @POST("/login")
    Call<String> loginTest(@Body UserLoginForm userLoginForm);
}

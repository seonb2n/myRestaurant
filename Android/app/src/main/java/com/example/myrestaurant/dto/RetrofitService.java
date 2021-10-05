package com.example.myrestaurant.dto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RetrofitService {

    @GET("/getUserData")
    Call<UserResult> getUser();

    @POST("/login")
    Call<LoginResponseForm> loginTest(@Body UserLoginForm userLoginForm);
}
package com.example.myrestaurant.dto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface RetrofitService {

    @GET("/getUserData")
    Call<UserResult> getUser();

    @POST("/login")
    Call<LoginResponseForm> logIn(@Body UserLoginForm userLoginForm);

    @POST("/addRestaurant")
    Call<List<Restaurant>> addRestaurant(@Header("Authorization") String token, @Body RestaurantEnrollForm restaurantEnrollForm);

    @GET("/getRestaurantList")
    Call<List<Restaurant>> getRestaurantList(@Header("Authorization") String token);
}

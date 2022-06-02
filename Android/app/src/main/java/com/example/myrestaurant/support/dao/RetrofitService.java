package com.example.myrestaurant.support.dao;

import com.example.myrestaurant.dto.LoginResponseForm;
import com.example.myrestaurant.dto.Restaurant;
import com.example.myrestaurant.dto.RestaurantUpdateDtoForm;
import com.example.myrestaurant.dto.UserLoginForm;
import com.example.myrestaurant.dto.UserRegisterDtoForm;
import com.example.myrestaurant.dto.UserResult;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface RetrofitService {

    @POST("login")
    Call<LoginResponseForm> logIn(@Body UserLoginForm userLoginForm);

    @POST("register")
    Call<LoginResponseForm> register(@Body UserRegisterDtoForm userRegisterDtoForm);

    @POST("/addRestaurant")
    Call<List<Restaurant>> addRestaurant(@Header("Authorization") String token, @Body RestaurantUpdateDtoForm restaurantEnrollForm);

    @POST("deleteRestaurant")
    Call<List<Restaurant>> deleteRestaurant(@Header("Authorization") String token, @Body String name);

    @GET("/getRestaurantList")
    Call<List<Restaurant>> getRestaurantList(@Header("Authorization") String token);

}

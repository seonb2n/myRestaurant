package com.example.myrestaurant.support;

import com.example.myrestaurant.dto.LoginResponseForm;
import com.example.myrestaurant.dto.Restaurant;
import com.example.myrestaurant.dto.RestaurantEnrollForm;
import com.example.myrestaurant.dto.UserLoginForm;
import com.example.myrestaurant.dto.UserResult;

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

    @POST("deleteRestaurant")
    Call<List<Restaurant>> deleteRestaurant(@Header("Authorization") String token, @Body String name);

    @GET("/getRestaurantList")
    Call<List<Restaurant>> getRestaurantList(@Header("Authorization") String token);

}

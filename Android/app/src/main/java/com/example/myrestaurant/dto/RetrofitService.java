package com.example.myrestaurant.dto;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RetrofitService {

    @GET("/getUserData")
    Call<UserResult> getUser();
}

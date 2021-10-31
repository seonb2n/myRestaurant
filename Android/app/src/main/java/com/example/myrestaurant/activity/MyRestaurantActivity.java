package com.example.myrestaurant.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myrestaurant.R;
import com.example.myrestaurant.dto.Restaurant;
import com.example.myrestaurant.support.RestaurantAdapter;

import static android.content.ContentValues.TAG;
import static com.example.myrestaurant.activity.LoginActivity.retrofitService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyRestaurantActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Restaurant> restaurantList = new ArrayList<>();
    SharedPreferences auto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myrestaurant);
        mRecyclerView = findViewById(R.id.restaurantList);
        auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);

        String authToken = "Bearer "+auto.getString("authToken", null);
        Call<List<Restaurant>> getRestaurantList = retrofitService.getRestaurantList(authToken);
        getRestaurantList.enqueue(new Callback<List<Restaurant>>() {
            @Override
            public void onResponse(Call<List<Restaurant>> call, Response<List<Restaurant>> response) {
                if(response.isSuccessful()) {
                    Log.d(TAG, "onResponse: 성공, 결과 \n"+response.body());
                    restaurantList = response.body();
                    Log.d(TAG, ">>> \n"+restaurantList.get(0).getName());

                    mRecyclerView.setHasFixedSize(true);
                    mLayoutManager = new LinearLayoutManager(MyRestaurantActivity.this, LinearLayoutManager.VERTICAL, false);
                    mRecyclerView.setLayoutManager(mLayoutManager);
                    final RestaurantAdapter restaurantAdapter = new RestaurantAdapter(restaurantList);
                    mRecyclerView.setAdapter(restaurantAdapter);

                } else {
                    Log.d(TAG, "onResponse: 실패");
                }
            }

            @Override
            public void onFailure(Call<List<Restaurant>> call, Throwable t) {

            }
        });

    }
}
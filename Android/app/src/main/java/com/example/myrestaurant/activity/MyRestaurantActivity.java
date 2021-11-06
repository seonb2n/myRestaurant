package com.example.myrestaurant.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myrestaurant.R;
import com.example.myrestaurant.dto.Restaurant;
import com.example.myrestaurant.support.MyAdapter;
import com.example.myrestaurant.support.RestaurantAdapter;
import com.example.myrestaurant.support.RetrofitService;
import com.example.myrestaurant.support.SwipeHelper;

import static android.content.ContentValues.TAG;
import static com.example.myrestaurant.activity.LoginActivity.retrofitService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MyRestaurantActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Restaurant> restaurantList = new ArrayList<>();
    SharedPreferences auto;
    ItemTouchHelper itemTouchHelper;
    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myrestaurant);
        mRecyclerView = findViewById(R.id.restaurantList);
        auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
        final String authToken = "Bearer "+auto.getString("authToken", null);
        Call<List<Restaurant>> getRestaurantList = retrofitService.getRestaurantList(authToken);

        alertDialog = createDialog();
        alertDialog.show();

        getRestaurantList.enqueue(new Callback<List<Restaurant>>() {
            @Override
            public void onResponse(Call<List<Restaurant>> call, Response<List<Restaurant>> response) {
                if(response.isSuccessful()) {
                    Log.d(TAG, "onResponse: 성공, 결과 \n"+response.body());
                    if(response.body() != null) {
                        restaurantList = response.body();
                    }
                    final RestaurantAdapter restaurantAdapter = new RestaurantAdapter(restaurantList);
                    mRecyclerView.setAdapter(restaurantAdapter);
                    restaurantAdapter.setOnItemClickListener(new RestaurantAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View v, int position) {
                            Intent intent = new Intent(getApplicationContext(), WebActivity.class);
                            intent.putExtra("Restaurant", restaurantList.get(position));
                            startActivity(intent);
                        }
                    });

                    SwipeHelper swipeHelper = new SwipeHelper(getApplicationContext(), mRecyclerView) {
                        @Override
                        public void instantiateUnderlayButton(final RecyclerView.ViewHolder viewHolder, List<UnderlayButton> underlayButtons) {
                            underlayButtons.add(new SwipeHelper.UnderlayButton(
                                    "Delete",
                                    0,
                                    Color.parseColor("#FF3C30"),
                                    new SwipeHelper.UnderlayButtonClickListener() {
                                        @Override
                                        public void onClick(int pos) {
                                            //TODO : onDelete
                                            deleteRestaurant(restaurantList.get(pos), authToken);
                                            restaurantList.remove(pos);
                                            restaurantAdapter.notifyItemRemoved(pos);
                                        }
                                    }
                            ));
                        }
                    };

                } else {
                    Log.d(TAG, "onResponse: 실패");
                }
                alertDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<Restaurant>> call, Throwable t) {
                alertDialog.dismiss();
            }
        });

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(MyRestaurantActivity.this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

    }

    private void deleteRestaurant(Restaurant restaurant, String token) {
        //TODO : retrofit 통신으로 서버 restaurant list 업데이트하기
        Toast.makeText(getApplicationContext(), "Delete" + restaurant.getName(), Toast.LENGTH_SHORT).show();
        Call<List<Restaurant>> deleteRestaurant = retrofitService.deleteRestaurant(token, restaurant.getName());

        deleteRestaurant.enqueue(new Callback<List<Restaurant>>() {
            @Override
            public void onResponse(Call<List<Restaurant>> call, Response<List<Restaurant>> response) {
            }

            @Override
            public void onFailure(Call<List<Restaurant>> call, Throwable t) {
            }
        });
    }

    private void setUpRecyclerView() {
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                itemTouchHelper.onDraw(c, parent, state);
            }
        });
    }

    private AlertDialog createDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MyRestaurantActivity.this);
        dialogBuilder.setCancelable(false);
        dialogBuilder.setView(R.layout.progress);
        AlertDialog dialog = dialogBuilder.create();
        return dialog;
    }
}

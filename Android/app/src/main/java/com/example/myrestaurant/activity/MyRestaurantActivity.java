package com.example.myrestaurant.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myrestaurant.R;
import com.example.myrestaurant.dto.restaurant.RestaurantEntity;
import com.example.myrestaurant.dto.restaurant.database.RestaurantDatabase;
import com.example.myrestaurant.support.RestaurantAdapter;
import com.example.myrestaurant.support.SwipeHelper;

import java.util.ArrayList;
import java.util.List;

public class MyRestaurantActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<RestaurantEntity> restaurantList = new ArrayList<>();
    SharedPreferences auto;
    ItemTouchHelper itemTouchHelper;
    AlertDialog alertDialog;
    RestaurantDatabase restaurantDatabase = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myrestaurant);
        mRecyclerView = findViewById(R.id.restaurantList);
        auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);

        alertDialog = createDialog();
        alertDialog.show();
        //restaurantList 가져오는 로직 필요
        getRestaurantListFromLocalDB();

    }

    private void getRestaurantListFromLocalDB() {
        restaurantDatabase = RestaurantDatabase.getInstance(this);
        SelectRunnable selectRunnable = new SelectRunnable();
        Thread selectThread = new Thread(selectRunnable);
        try {
            selectThread.start();
        } catch (Exception ignored) {
        }
        finally {
            initViews();
            alertDialog.dismiss();
        }
    }

    class SelectRunnable implements Runnable {
        @Override
        public void run() {
            try {
                restaurantList.addAll(restaurantDatabase.restaurantDao().getAll());
            } catch (Exception e) {
                Log.d(TAG, "local db 로부터 데이터를 가져오는데 실패했습니다.");
            }
        }
    }

    private void initViews() {
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
                                restaurantList.remove(pos);
                                restaurantAdapter.notifyItemRemoved(pos);
                            }
                        }
                ));
            }
        };
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(MyRestaurantActivity.this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
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

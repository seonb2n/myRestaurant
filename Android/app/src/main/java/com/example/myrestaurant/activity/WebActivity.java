package com.example.myrestaurant.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myrestaurant.R;
import com.example.myrestaurant.dto.Restaurant;
import com.example.myrestaurant.dto.RestaurantEnrollForm;
import com.example.myrestaurant.dto.RetrofitService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import static android.content.ContentValues.TAG;

import static com.example.myrestaurant.activity.LoginActivity.retrofit;
import static com.example.myrestaurant.activity.LoginActivity.retrofitService;

public class WebActivity extends AppCompatActivity {

    private WebView webView;
    private FloatingActionButton fab;
    static final int INTERNET_PERMISSON=1;
    private final String BASEURL = "http://172.30.1.13:8833/";
    SharedPreferences auto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        webView = findViewById(R.id.webView);
        fab = findViewById(R.id.floatingBtn);

        Intent intent = getIntent();

        final Restaurant restaurant = (Restaurant) intent.getSerializableExtra("Restaurant");
        String url = restaurant.getLink();
        auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);

        webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());
        webView.setNetworkAvailable(true);
        webView.getSettings().setJavaScriptEnabled(true);

        webView.getSettings().setDomStorageEnabled(true);

        webView.loadUrl(url);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
                // Restaurant 를 서버에 추가

                RestaurantEnrollForm restaurantEnrollForm = new RestaurantEnrollForm(
                        restaurant.getName(),
                        restaurant.getLocation(),
                        restaurant.getLink(),
                        restaurant.getCategory(),
                        auto.getString("inputId", null)
                );

                String authToken = "Bearer "+auto.getString("authToken", null);

                Call<List<Restaurant>> enrollRestaurant = retrofitService.addRestaurant(authToken, restaurantEnrollForm);


                enrollRestaurant.enqueue(new Callback<List<Restaurant>>() {
                    @Override
                    public void onResponse(Call<List<Restaurant>> call, Response<List<Restaurant>> response) {
                        if(response.isSuccessful()) {
                            Log.d(TAG, "onResponse: 성공, 결과 \n"+response.body());
                        } else {
                            Log.d(TAG, "onResponse: 실패");
                        }

                    }

                    @Override
                    public void onFailure(Call<List<Restaurant>> call, Throwable t) {

                    }
                });

            }
        });

    }

}

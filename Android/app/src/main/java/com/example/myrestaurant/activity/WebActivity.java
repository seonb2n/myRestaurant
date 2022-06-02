package com.example.myrestaurant.activity;

import android.annotation.SuppressLint;
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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myrestaurant.R;
import com.example.myrestaurant.dto.Restaurant;
import com.example.myrestaurant.dto.RestaurantUpdateDtoForm;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

import static com.example.myrestaurant.activity.LoginActivity.retrofitService;

public class WebActivity extends AppCompatActivity {

    private WebView webView;
    private FloatingActionButton fab;
    static final int INTERNET_PERMISSON=1;
    private final String BASEURL = "http://172.30.1.13:8833/api/v1/users";
    SharedPreferences auto;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        webView = findViewById(R.id.webView);
        fab = findViewById(R.id.floatingBtn);

        Intent intent = getIntent();

        final Restaurant restaurant = (Restaurant) intent.getSerializableExtra("Restaurant");
        String url = restaurant.getLink();
        Log.d(TAG, ">>> " + restaurant.toString());

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

                RestaurantUpdateDtoForm restaurantEnrollForm = new RestaurantUpdateDtoForm(
                        restaurant.getName(),
                        restaurant.getLocation(),
                        restaurant.getLink(),
                        restaurant.getCategory()
                );
                //todo 버튼이 눌리면 restaurant local db 에 추가

            }
        });

    }

}

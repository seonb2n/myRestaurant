package com.example.myrestaurant.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myrestaurant.R;
import com.example.myrestaurant.dto.Restaurant;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class WebActivity extends AppCompatActivity {

    private WebView webView;
    private FloatingActionButton fab;
    static final int INTERNET_PERMISSON=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        webView = findViewById(R.id.webView);
        fab = findViewById(R.id.floatingBtn);

        Intent intent = getIntent();

        Restaurant restaurant = (Restaurant) intent.getSerializableExtra("Restaurant");
        String url = restaurant.getLink();

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

                

            }
        });

    }

}

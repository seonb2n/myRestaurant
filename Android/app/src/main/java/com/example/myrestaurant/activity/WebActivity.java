package com.example.myrestaurant.activity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myrestaurant.R;
import com.example.myrestaurant.dto.Restaurant;

public class WebActivity extends AppCompatActivity {

    private WebView webView;
    static final int INTERNET_PERMISSON=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        webView = findViewById(R.id.webView);

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
    }

}

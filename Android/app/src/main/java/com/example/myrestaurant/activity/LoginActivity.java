package com.example.myrestaurant.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myrestaurant.R;
import com.example.myrestaurant.dto.RetrofitService;
import com.example.myrestaurant.dto.UserLoginForm;
import com.example.myrestaurant.dto.LoginResponseForm;
import com.example.myrestaurant.dto.UserResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static android.content.ContentValues.TAG;

public class LoginActivity extends AppCompatActivity {

    Button buttonLogin;
    Button buttonJoin;
    Button buttonJoinWithNaver;
    EditText textViewId;
    EditText textViewPw;

    static final int INTERNET_PERMISSON=1;

    private Retrofit retrofit;
    private RetrofitService retrofitService;
    private final String BASEURL = "http://172.30.1.13:8833/";

    static String loginSuccessId;
    static String loginSuccessPw;
    private SharedPreferences auto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonJoin = findViewById(R.id.buttonJoin);
        buttonJoinWithNaver = findViewById(R.id.buttonJoinNaver);
        textViewId = findViewById(R.id.textViewId);
        textViewPw = findViewById(R.id.textViewPw);
        auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);

        int permissonCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET);

        if(permissonCheck == PackageManager.PERMISSION_GRANTED){
            Toast.makeText(getApplicationContext(), "인터넷 접속 권한 있음", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(), "인터넷 접속 권한 있음", Toast.LENGTH_SHORT).show();

            //권한설정 dialog에서 거부를 누르면
            //ActivityCompat.shouldShowRequestPermissionRationale 메소드의 반환값이 true가 된다.
            //단, 사용자가 "Don't ask again"을 체크한 경우
            //거부하더라도 false를 반환하여, 직접 사용자가 권한을 부여하지 않는 이상, 권한을 요청할 수 없게 된다.
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.INTERNET)){
                //이곳에 권한이 왜 필요한지 설명하는 Toast나 dialog를 띄워준 후, 다시 권한을 요청한다.
                Toast.makeText(getApplicationContext(), "인터넷 접속 권한이 필요합니다", Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(this, new String[]{ Manifest.permission.INTERNET}, INTERNET_PERMISSON);
            }else{
                ActivityCompat.requestPermissions(this, new String[]{ Manifest.permission.RECEIVE_SMS}, INTERNET_PERMISSON);
            }
        }

        String loginId = auto.getString("inputId", null);
        String loginPwd = auto.getString("inputPwd", null);
        tryLogin(loginId, loginPwd);

    }

    private void setRetrofitInit() {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASEURL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        retrofitService = retrofit.create(RetrofitService.class);

        Call<UserResult> call = retrofitService.getUser();

        call.enqueue(new Callback<UserResult>() {
            @Override
            public void onResponse(Call<UserResult> call, Response<UserResult> response) {
                if(response.isSuccessful()) {
                    UserResult result = response.body();
                    Log.d(TAG, "onResponse: 성공, 결과 \n"+result.toString());
                } else {
                    Log.d(TAG, "onResponse: 실패");
                }
            }

            @Override
            public void onFailure(Call<UserResult> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    private void tryLogin(String id, String password) {
        final UserLoginForm userLoginForm = new UserLoginForm(id, password);
        Call<LoginResponseForm> loginTest = retrofitService.loginTest(userLoginForm);
        loginTest.enqueue(new Callback<LoginResponseForm>() {
            @Override
            public void onResponse(Call<LoginResponseForm> call, Response<LoginResponseForm> response) {
                if(response.isSuccessful()) {
                    LoginResponseForm responseForm = response.body();
                    Log.d(TAG, "onResponse: 성공, 결과 \n"+responseForm);
                    SharedPreferences.Editor autoLogin = auto.edit();
                    autoLogin.putString("inputId", userLoginForm.getEmail());
                    autoLogin.putString("inputPwd", userLoginForm.getPassword());
                    autoLogin.commit();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Log.d(TAG, "onResponse: 실패 \n" + response.body());
                }
            }

            @Override
            public void onFailure(Call<LoginResponseForm> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    public void onButtonLoginClicked(View v) {

        String id = textViewId.getText().toString();
        String password = textViewPw.getText().toString();

        tryLogin(id, password);
    }
}
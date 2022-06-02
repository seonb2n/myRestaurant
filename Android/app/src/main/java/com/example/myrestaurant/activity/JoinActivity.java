package com.example.myrestaurant.activity;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import static com.example.myrestaurant.activity.LoginActivity.retrofitService;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.example.myrestaurant.R;
import com.example.myrestaurant.databinding.ActivityJoinBinding;
import com.example.myrestaurant.dto.LoginResponseForm;
import com.example.myrestaurant.dto.UserLoginForm;
import com.example.myrestaurant.dto.UserRegisterDtoForm;
import com.example.myrestaurant.support.dao.RetrofitService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class JoinActivity extends AppCompatActivity {

    private ActivityJoinBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        binding = ActivityJoinBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buttonJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.joinTextViewEmail.getText().toString();
                String password = binding.joinTextViewPassword.getText().toString();
                String nickName = binding.joinTextViewNickName.getText().toString();
                tryRegister(email, password, nickName);
            }
        });
    }

    private void tryRegister(String email, String password, String nickName) {
        final UserRegisterDtoForm userRegisterDtoForm = new UserRegisterDtoForm(email, password, nickName);
        Call<LoginResponseForm> enrollTest = retrofitService.register(userRegisterDtoForm);
        enrollTest.enqueue(new Callback<LoginResponseForm>() {
            @Override
            public void onResponse(Call<LoginResponseForm> call, Response<LoginResponseForm> response) {
                if(response.isSuccessful()) {
                    LoginResponseForm responseForm = response.body();
                    if(responseForm.getResult().equals("SUCCESS")) {
                        Log.d(TAG, "onResponse: 성공, 결과 \n"+responseForm);
                        SharedPreferences.Editor autoLogin = getSharedPreferences("auto", Activity.MODE_PRIVATE).edit();
                        autoLogin.putString("userToken", responseForm.getData().getUserToken());
                        autoLogin.putString("inputId", userRegisterDtoForm.getEmail());
                        autoLogin.putString("inputPwd", userRegisterDtoForm.getPassword());
                        autoLogin.commit();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), responseForm.getResponseMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponseForm> call, Throwable t) {

            }
        });
    }

}
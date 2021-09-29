package com.example.myrestaurant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    Button buttonLogin;
    Button buttonJoin;
    Button buttonJoinWithNaver;
    EditText textViewId;
    EditText textViewPw;

    static final int INTERNET_PERMISSON=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonJoin = findViewById(R.id.buttonJoin);
        buttonJoinWithNaver = findViewById(R.id.buttonJoinNaver);
        textViewId = findViewById(R.id.textViewId);
        textViewPw = findViewById(R.id.textViewPw);

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
    }

    public void onButtonLoginClicked(View v) {
        String sendmsg = "log_in";

        String id = textViewId.getText().toString();
        String password = textViewPw.getText().toString();

        //TODO Spring server 로부터, 해당 id, pw 가 등록된 유저인지 확인하는 task 만들기

    }
}
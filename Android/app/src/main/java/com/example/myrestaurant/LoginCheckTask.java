package com.example.myrestaurant;

import android.os.AsyncTask;

public class LoginCheckTask extends AsyncTask<String, Void, String> {

    String sendMsg, receiveMsg;
    String serverIp;

    LoginCheckTask(String sendMsg) {
        this.sendMsg = sendMsg;
    }

    @Override
    protected String doInBackground(String... strings) {

        //TODO Spring server 와 통신해서 id, pw 가 적절한 값인지 확인 후 결과 리턴

        return null;
    }

}

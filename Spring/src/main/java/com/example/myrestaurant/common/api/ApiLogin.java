package com.example.myrestaurant.common.api;

import com.google.gson.JsonObject;

import javax.servlet.http.HttpSession;
import java.io.IOException;

public interface ApiLogin {

    public String getAuthUrl(HttpSession httpSession);

    public String getAuthToken(String code, String state) throws IOException;

    public String getUserProfile(String authToken, String refreshToken) throws IOException;

    public String getUserNickName(String responseBody);

}

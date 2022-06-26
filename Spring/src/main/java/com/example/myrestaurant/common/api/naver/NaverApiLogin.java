package com.example.myrestaurant.common.api.naver;

import com.example.myrestaurant.common.api.ApiLogin;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.UUID;

@Component
public class NaverApiLogin implements ApiLogin {

    private final static String CLIENT_ID = "K9c0e2mHE0hxR5xftO3o";
    private final static String CLIENT_SECRET = "zhSUd9KZsN";
    private final static String REDIRECT_URI = "http://localhost:8833/naver/callback";
    private final static String SESSION_STATE = "oauth_state";
    private final static String USER_PROFILE_API_URL = "https://openapi.naver.com/v1/nid/me";


    @Override
    public String getAuthUrl(HttpSession httpSession) {
        String state = generateRandomString();
        setSession(httpSession, state);

        OAuth20Service oAuth20Service = new ServiceBuilder()
                .apiKey(CLIENT_ID)
                .apiSecret(CLIENT_SECRET)
                .callback(REDIRECT_URI)
                .state(state)
                .build(NaverLoginApi.getInstance());

        return oAuth20Service.getAuthorizationUrl();
    }

    @Override
    public String getAuthToken(String code, String state) throws IOException {
        OAuth20Service oAuth20Service = new ServiceBuilder()
                .apiKey(CLIENT_ID)
                .apiSecret(CLIENT_SECRET)
                .callback(REDIRECT_URI)
                .state(state)
                .build(NaverLoginApi.getInstance());
        OAuth2AccessToken accessToken = oAuth20Service.getAccessToken(code);
        return accessToken.getAccessToken();
    }

    @Override
    public String getUserProfile(String authToken, String refreshToken) throws IOException {
        OAuth20Service oAuth20Service = new ServiceBuilder()
                .apiKey(CLIENT_ID)
                .apiSecret(CLIENT_SECRET)
                .callback(REDIRECT_URI)
                .build(NaverLoginApi.getInstance());
        OAuthRequest request = new OAuthRequest(Verb.GET, USER_PROFILE_API_URL, oAuth20Service);
        oAuth20Service.signRequest(new OAuth2AccessToken(authToken), request);
        Response response = request.send();
        return response.getBody();
    }

    @Override
    public String getUserNickName(String responseBody) {
        JsonParser parser = new JsonParser();
        Object obj = parser.parse(responseBody);
        JsonObject jsonObject = (JsonObject) obj;
        JsonObject response_obj = (JsonObject) jsonObject.get("response");
        return response_obj.get("nickname").toString();
    }

    private void setSession(HttpSession httpSession, String state) {
        httpSession.setAttribute(SESSION_STATE, state);
    }

    private String generateRandomString() {
        return UUID.randomUUID().toString();
    }
}

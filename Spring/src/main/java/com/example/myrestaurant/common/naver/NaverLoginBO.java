package com.example.myrestaurant.common.naver;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpSession;
import java.io.IOError;
import java.io.IOException;
import java.util.UUID;

@Component
public class NaverLoginBO {

    private final static String CLIENT_ID = "K9c0e2mHE0hxR5xftO3o";
    private final static String CLIENT_SECRET = "K9c0e2mHE0hxR5xftO3o";
    private final static String REDIRECT_URI = "http://localhost:8833/login/oauth2/naver";
    private final static String SESSION_STATE = "oauth_state";

    private final static String PROFILE_API_URL = "https://openapi.naver.com/v1/nid/me";

    public String getAuthorizationUrl(HttpSession httpSession) {
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

    //네이버 아이디로 callback 처리 및 accessToken 획득 메서드
    public OAuth2AccessToken getAccessToken(HttpSession session, String code, String state) throws IOException {
        String sessionState = getSession(session);
        if(StringUtils.pathEquals(sessionState, state)) {
            OAuth20Service oAuth20Service = new ServiceBuilder()
                    .apiKey(CLIENT_ID)
                    .apiSecret(CLIENT_SECRET)
                    .callback(REDIRECT_URI)
                    .state(state)
                    .build(NaverLoginApi.getInstance());
            OAuth2AccessToken accessToken = oAuth20Service.getAccessToken(code);
            return accessToken;
        }
        return null;
    }

    private String getSession(HttpSession session) {
        return (String) session.getAttribute(SESSION_STATE);
    }

    private void setSession(HttpSession httpSession, String state) {
        httpSession.setAttribute(SESSION_STATE, state);
    }

    private String generateRandomString() {
        return UUID.randomUUID().toString();
    }

    //Access Token 을 이용하여 네이버 사용자 프로필 API 호출
    public String getUserProfile(OAuth2AccessToken oAuth2AccessToken) throws IOException {

        OAuth20Service oAuth20Service = new ServiceBuilder()
                .apiKey(CLIENT_ID)
                .apiSecret(CLIENT_SECRET)
                .callback(REDIRECT_URI).build(NaverLoginApi.getInstance());

        OAuthRequest request = new OAuthRequest(Verb.GET, PROFILE_API_URL, oAuth20Service);
        oAuth20Service.signRequest(oAuth2AccessToken, request);

        Response response = request.send();
        return response.getBody();
    }

}

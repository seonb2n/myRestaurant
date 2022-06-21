package com.example.myrestaurant.common.naver;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

import static java.lang.System.out;

@Component
public class NaverLoginBO {

    private final static String CLIENT_ID = "K9c0e2mHE0hxR5xftO3o";
    private final static String CLIENT_SECRET = "zhSUd9KZsN";
    private final static String REDIRECT_URI = "http://localhost:8833/naver/callback";
    private final static String SESSION_STATE = "oauth_state";

    private final static String TOKEN_API_URL = "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code&";


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
    public String getAccessToken(HttpSession session, String code, String state) throws IOException {
        String apiURL;
        apiURL = TOKEN_API_URL;
        apiURL += "client_id=" + CLIENT_ID;
        apiURL += "&client_secret=" + CLIENT_SECRET;
        apiURL += "&redirect_uri=" + REDIRECT_URI;
        apiURL += "&code=" + code;
        apiURL += "&state" + state;
        String accessToken = "";
        String refreshToken = "";
        out.println(">>> apiURL:"+apiURL);

        try {
            URL url = new URL(apiURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
            BufferedReader br;
            out.print("responseCode="+responseCode);
            if(responseCode==200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            }
            String inputLine;
            StringBuffer res = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                res.append(inputLine);
            }
            br.close();
            if(responseCode==200) {
                out.println(res);
            }
            JsonParser jsonParser = new JsonParser();
            Object obj = jsonParser.parse(res.toString());
            JsonObject jsonObject = (JsonObject) obj;
            accessToken = jsonObject.get("access_token").toString();
            refreshToken = jsonObject.get("refresh_token").toString();

        } catch (Exception e) {
            out.println(e);
        }
        return accessToken;
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
    public String getUserProfile(String accessToken) {
        return NaverMemberProfileGetter.run(accessToken);
    }

}

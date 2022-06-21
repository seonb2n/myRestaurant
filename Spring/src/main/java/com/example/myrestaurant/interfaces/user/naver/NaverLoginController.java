package com.example.myrestaurant.interfaces.user.naver;

import com.example.myrestaurant.common.naver.NaverLoginBO;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.json.JSONParser;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/naver")
@Slf4j
public class NaverLoginController {

    private final NaverLoginBO naverLoginBO;
    private String apiResult = null;

    @RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST})
    public String login(Model model, HttpSession httpSession) {
        String naverAuthUrl = naverLoginBO.getAuthorizationUrl(httpSession);
        log.info(">>> 네이버 : " + naverAuthUrl);

        model.addAttribute("url", naverAuthUrl);
        return "login";
    }

    @RequestMapping(value = "/callback", method = {RequestMethod.GET, RequestMethod.POST})
    public String callback(Model model, @RequestParam String code, @RequestParam String state, HttpSession session) throws IOException {
        log.info(">>> callback");
        OAuth2AccessToken oauthToken;
        oauthToken = naverLoginBO.getAccessToken(session, code, state);
        //1. 로그인 사용자 정보를 읽어온다.
        apiResult = naverLoginBO.getUserProfile(oauthToken);

        JsonParser parser = new JsonParser();
        Object obj = parser.parse(apiResult);
        JsonObject jsonObject = (JsonObject) obj;

        JsonObject response_obj = (JsonObject) jsonObject.get("response");
        String nickName = response_obj.get("nickname").toString();
        log.info(">>> 닉네임: " + nickName);
        session.setAttribute("sessionId", nickName);
        model.addAttribute("result", apiResult);

        return "naverSuccess";
    }


}

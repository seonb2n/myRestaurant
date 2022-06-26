package com.example.myrestaurant.interfaces.user.naver;

import com.example.myrestaurant.common.api.naver.NaverApiLogin;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/naver")
@Slf4j
public class NaverLoginController {

    private final NaverApiLogin naverApiLogin;
    private String apiResult = null;

    @RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST})
    public String login(Model model, HttpSession httpSession) {
        String naverAuthUrl = naverApiLogin.getAuthUrl(httpSession);
        log.info(">>> 네이버 : " + naverAuthUrl);
        model.addAttribute("url", naverAuthUrl);
        return "login";
    }

    @RequestMapping(value = "/callback", method = {RequestMethod.GET, RequestMethod.POST})
    public String callback(Model model, @RequestParam String code, @RequestParam String state, HttpSession session) throws IOException {
        log.info(">>> callback");

        String oauthToken = naverApiLogin.getAuthToken(code, state);
        apiResult = naverApiLogin.getUserProfile(oauthToken, null);
        String nickName = naverApiLogin.getUserNickName(apiResult);

        log.info(">>> 닉네임: " + nickName);
        session.setAttribute("sessionId", nickName);
        model.addAttribute("result", apiResult);

        return "naverSuccess";
    }


}

package com.example.myrestaurant.controller;

import com.example.myrestaurant.config.UserEnrollForm;
import com.example.myrestaurant.config.UserLoginForm;
import com.example.myrestaurant.dto.user.domain.User;
import com.example.myrestaurant.dto.user.repository.UserRepository;
import com.example.myrestaurant.dto.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class HomeController {

    private final UserService userService;

    @GetMapping("/")
    public String index() {
        return "Connect";
    }

    @RequestMapping("/userEnroll")
    public User userEnroll() {
        UserEnrollForm form1 = UserEnrollForm.builder()
                .email("test")
                .password("1111")
                .build();
        return userService.enroll(form1);
    }

    @GetMapping("/getUserData")
    public User getUserData() {
        return userService.findUser("test").get();
    }

    @RequestMapping(value = "/login")
    public User login(@RequestBody UserLoginForm userLoginForm) {
        return userService.login(userLoginForm);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping("/greeting")
    public String greeting(@AuthenticationPrincipal User user) {
        return "hello " + user.getEmail();
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/getLoginUserData")
    public User getLoginUserData(@AuthenticationPrincipal User user) {
        return userService.findUser(user.getEmail()).get();
    }

}

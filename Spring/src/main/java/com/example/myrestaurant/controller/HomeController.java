package com.example.myrestaurant.controller;

import com.example.myrestaurant.config.UserEnrollForm;
import com.example.myrestaurant.dto.user.domain.User;
import com.example.myrestaurant.dto.user.repository.UserRepository;
import com.example.myrestaurant.dto.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HomeController {

    private final UserService userService;

    @GetMapping("/")
    public String index() {
        return "Connect";
    }

    @GetMapping("/userEnroll")
    public User userEnroll() {
        UserEnrollForm form1 = UserEnrollForm.builder()
                .email("test")
                .password("1111")
                .build();
        return userService.enroll(form1);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/greeting")
    public String greeting() {
        return "hello";
    }

}

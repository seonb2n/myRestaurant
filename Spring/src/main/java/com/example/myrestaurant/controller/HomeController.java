package com.example.myrestaurant.controller;

import com.example.myrestaurant.config.UserEnrollForm;
import com.example.myrestaurant.config.UserLoginForm;
import com.example.myrestaurant.dto.restaurant.domain.Restaurant;
import com.example.myrestaurant.dto.restaurant.domain.RestaurantEnrollForm;
import com.example.myrestaurant.dto.user.domain.User;
import com.example.myrestaurant.dto.user.repository.UserRepository;
import com.example.myrestaurant.dto.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

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
    public String greeting(Authentication authentication, Principal principal) {
        return "hello " + authentication.getName();
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping("/addRestaurant")
    public List<Restaurant> addRestaurant(@RequestBody RestaurantEnrollForm enrollForm) {
        User user = userService.findUser(enrollForm.getUserEmail()).get();
        return userService.addRestaurant(user ,Restaurant.builder()
                .user(user)
                .category(enrollForm.getCategory())
                .name(enrollForm.getName())
                .link(enrollForm.getLink())
                .location(enrollForm.getLocation())
                .build());
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/getLoginUserData")
    public User getLoginUserData(@AuthenticationPrincipal User user) {
        return userService.findUser(user.getEmail()).get();
    }

}

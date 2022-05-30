package com.example.myrestaurant.interfaces.user.controller;

import com.example.myrestaurant.common.config.UserEnrollForm;
import com.example.myrestaurant.common.config.UserLoginForm;
import com.example.myrestaurant.domain.restaurant.domain.Restaurant;
import com.example.myrestaurant.domain.restaurant.domain.RestaurantEnrollForm;
import com.example.myrestaurant.infrastructure.restaurant.RestaurantServiceImpl;
import com.example.myrestaurant.domain.user.domain.User;
import com.example.myrestaurant.infrastructure.user.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class HomeController {

    private final UserServiceImpl userService;
    private final RestaurantServiceImpl restaurantService;

    @GetMapping("/")
    public String index() {
        return "Connect";
    }

    //TODO 사용자 회원가입 구현
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
        Restaurant restaurant = Restaurant.builder()
                .name(enrollForm.getName())
                .link(enrollForm.getLink())
                .category(enrollForm.getCategory())
                .location(enrollForm.getLocation())
                .build();
        userService.addRestaurant(user, restaurant);
        System.out.println(userService.getRestaurants(user));
        return new ArrayList<>(userService.getRestaurants(user));
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping("/deleteRestaurant")
    public List<Restaurant> deleteRestaurant(Authentication authentication, @RequestBody String name) {
        User user = userService.findUser(authentication.getName()).get();
        userService.deleteRestaurant(user, userService.getRestaurantByName(user, name));
        return new ArrayList<>(userService.getRestaurants(user));
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/getLoginUserData")
    public User getLoginUserData(@AuthenticationPrincipal User user) {
        return userService.findUser(user.getEmail()).get();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/getRestaurantList")
    public List<Restaurant> getRestaurantList(Authentication authentication, Principal principal) {
        User user = userService.findUser(authentication.getName()).get();
        return new ArrayList<>(userService.getRestaurants(user));
    }
}

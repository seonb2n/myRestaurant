package com.example.myrestaurant.controller;

import com.example.myrestaurant.config.UserEnrollForm;
import com.example.myrestaurant.config.UserLoginForm;
import com.example.myrestaurant.dto.restaurant.domain.Restaurant;
import com.example.myrestaurant.dto.restaurant.domain.RestaurantEnrollForm;
import com.example.myrestaurant.dto.restaurant.service.RestaurantService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class HomeController {

    private final UserService userService;
    private final RestaurantService restaurantService;

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
    public void deleteRestaurant(@RequestBody RestaurantEnrollForm enrollForm) {
        User user = userService.findUser(enrollForm.getUserEmail()).get();
        Optional<Restaurant> restaurant = restaurantService.findByName(enrollForm.getName());
        if(restaurant.isPresent()) {
            userService.deleteRestaurant(user, restaurant.get());
        }
        else {
            System.out.println(enrollForm.getName() + " is not exist");
        }

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

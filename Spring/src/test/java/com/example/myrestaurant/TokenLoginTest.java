package com.example.myrestaurant;

import com.example.myrestaurant.config.TokenBox;
import com.example.myrestaurant.config.UserLoginForm;
import com.example.myrestaurant.dto.restaurant.domain.Restaurant;
import com.example.myrestaurant.dto.user.domain.User;
import com.example.myrestaurant.dto.user.repository.UserRepository;
import com.example.myrestaurant.dto.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TokenLoginTest extends WebIntegrationTest{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @BeforeEach
    void before() {

        userRepository.deleteAll();

        Set<Restaurant> restaurants1 = new HashSet<>();
        restaurants1.add(Restaurant.builder()
                .name("성산짜장")
                .build());

        restaurants1.add(Restaurant.builder()
                .name("반올림피자")
                .build());

        User user = userService.save(User.builder()
                .email("user1@naver.com")
                .password("1111")
                .restaurantList(restaurants1)
                .enabled(true)
                .build());
        userService.addAuthority(user.getUserId(), "ROLE_USER");
    }

    private TokenBox getToken() {
        RestTemplate client = new RestTemplate();

        HttpEntity<UserLoginForm> entity = new HttpEntity<>(
                UserLoginForm.builder()
                .email("user1@naver.com")
                .password("1111")
                .build()
        );

        ResponseEntity<User> resp = client.exchange(uri("/login"), HttpMethod.POST, entity, User.class);

        return TokenBox.builder().authToken(resp.getHeaders().get("auth_token").get(0))
                .refreshToken(resp.getHeaders().get("refresh_token").get(0))
                .build();

    }

    private TokenBox refreshToken(String refreshToken) {
        RestTemplate client = new RestTemplate();

        HttpEntity<UserLoginForm> entity = new HttpEntity<>(
                UserLoginForm.builder()
                        .refreshToken(refreshToken)
                        .build()
        );

        ResponseEntity<User> resp = client.exchange(uri("/login"), HttpMethod.POST, entity, User.class);
        System.out.println(resp);

        return TokenBox.builder().authToken(resp.getHeaders().get("auth_token").get(0))
                .refreshToken(resp.getHeaders().get("refresh_token").get(0))
                .build();
    }

    @DisplayName("1. 가져온 토큰을 통해서, login을 실시한다.")
    @Test
    void test_1() {
        TokenBox token = getToken();
        System.out.println(token);

        RestTemplate client = new RestTemplate();
        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.AUTHORIZATION, "Bearer " + token.getAuthToken());
        HttpEntity body = new HttpEntity<>(null, header);
        ResponseEntity<User> resp2 = client.exchange(uri("/login"), HttpMethod.POST, body, User.class);
        System.out.println(resp2.getBody());
    }
}

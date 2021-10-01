package com.example.myrestaurant;

import com.example.myrestaurant.dto.restaurant.domain.Restaurant;
import com.example.myrestaurant.dto.user.domain.User;
import com.example.myrestaurant.dto.user.repository.UserRepository;
import com.example.myrestaurant.dto.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserServiceTest extends WebIntegrationTest{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    private String greetingUrl() {
        return "http://localhost:"+port+"/greeting";
    }

    @BeforeEach
    void before() {

        Set<Restaurant> restaurants = new HashSet<>();
        restaurants.add(Restaurant.builder()
                .name("성산짜장")
                .build());

        restaurants.add(Restaurant.builder()
                .name("반올림피자")
                .build());

        userRepository.deleteAll();
        User user = userService.save(User.builder()
                .email("user1@naver.com")
                .password("1111")
                .nickName("TestUser01")
                .restaurantList(restaurants)
                .enabled(true)
                .build());

        userService.addAuthority(user.getUserId(), "ROLE_USER");
    }

    @Test
    void test_1() {
        //hello message 를 받는다.
        TestRestTemplate testClient = new TestRestTemplate("user1@naver.com", "1111");
        ResponseEntity<String> responseEntity = testClient.getForEntity(uri("/greeting"), String.class);
        assertEquals("hello", responseEntity.getBody());
        System.out.println(responseEntity.getBody());
    }

    @Test
    void test_2() {
        //user service 테스트
        User user1 = userRepository.findUserByEmail("user1@naver.com").get();
        Restaurant r1 = Restaurant.builder().name("장인족발").build();
        Restaurant r2 = Restaurant.builder().name("BBQ 치킨").build();
        userService.addRestaurants(user1, r1, r2);

        System.out.println(userService.findUser("user1@naver.com"));
    }

}

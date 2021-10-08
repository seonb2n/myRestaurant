package com.example.myrestaurant;

import com.example.myrestaurant.dto.restaurant.domain.Restaurant;
import com.example.myrestaurant.dto.restaurant.respository.RestaurantRepository;
import com.example.myrestaurant.dto.user.domain.User;
import com.example.myrestaurant.dto.user.repository.UserRepository;
import com.example.myrestaurant.dto.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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

        userRepository.deleteAll();

        Set<Restaurant> restaurants1 = new HashSet<>();
        restaurants1.add(Restaurant.builder()
                .name("성산짜장")
                .build());

        restaurants1.add(Restaurant.builder()
                .name("반올림피자")
                .build());

        User user1 = userService.save(User.builder()
                .email("user1@naver.com")
                .password("1111")
                .nickName("TestUser01")
                .restaurantList(restaurants1)
                .enabled(true)
                .build());

        userService.addAuthority(user1.getUserId(), "ROLE_USER");


        Set<Restaurant> restaurants2 = new HashSet<>();
        restaurants2.add(Restaurant.builder()
                .name("성산짜장")
                .build());

        restaurants2.add(Restaurant.builder()
                .name("bbq 치킨")
                .build());

        User user2 = userService.save(User.builder()
                .email("user2@naver.com")
                .password("2222")
                .nickName("TestUser02")
                .restaurantList(restaurants2)
                .enabled(true)
                .build());

        userService.addAuthority(user2.getUserId(), "ROLE_USER");

    }

    @Test
    void test_1() {
        //hello message 를 받는다.
        TestRestTemplate testClient = new TestRestTemplate("user1@naver.com", "1111");
        ResponseEntity<String> responseEntity = testClient.getForEntity(uri("/greeting"), String.class);
  //      assertEquals("hello", responseEntity.getBody());
        System.out.println(responseEntity.getBody());
    }

    @Test
    void test_1_1() {
        TestRestTemplate testClient = new TestRestTemplate("user1@naver.com", "1111");
        ResponseEntity<User> responseEntity = testClient.getForEntity(uri("/login"), User.class);
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

    @DisplayName("User 와 Restaurant DB 에 대한 테스트")
    @Test
    void test_3() {
        User user1 = userService.findUser("user1@naver.com").get();
        System.out.println(user1.getRestaurantList());

        User user2 = userService.findUser("user2@naver.com").get();
        System.out.println(user2.getRestaurantList());

        userService.deleteRestaurant(user2, userService.getRestaurant(user2, 4L));

        System.out.println(user1.getRestaurantList());
        System.out.println(user2.getRestaurantList());
}

}

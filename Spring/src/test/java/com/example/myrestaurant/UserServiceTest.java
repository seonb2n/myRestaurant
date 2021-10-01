package com.example.myrestaurant;

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
        User user = userService.save(User.builder()
                .email("user1@naver.com")
                .password("1111")
                .nickName("TestUser01")
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

}

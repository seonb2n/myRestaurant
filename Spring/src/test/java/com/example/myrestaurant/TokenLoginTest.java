package com.example.myrestaurant;

import com.example.myrestaurant.dto.user.domain.User;
import com.example.myrestaurant.dto.user.repository.UserRepository;
import com.example.myrestaurant.dto.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

public class TokenLoginTest extends WebIntegrationTest{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @BeforeEach
    void before() {

        userRepository.deleteAll();
        User user = userService.save(User.builder()
                .email("user1@naver.com")
                .password("1111")
                .enabled(true)
                .build());
        userService.addAuthority(user.getUserId(), "ROLE_USER");
    }
}

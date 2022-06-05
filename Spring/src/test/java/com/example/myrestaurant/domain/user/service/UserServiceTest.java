package com.example.myrestaurant.domain.user.service;

import com.example.myrestaurant.domain.user.domain.User;
import com.example.myrestaurant.infrastructures.user.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    public void iniUserTest() throws Exception {
        //given
        User user = User.builder()
                .email("test1@naver.com")
                .nickName("test-user1")
                .password("1234")
                .build();

        userRepository.save(user);

        //when
        User foundUser = userService.getUserWithUserToken(user.getUserToken());

        //then
        Assertions.assertEquals(user.getEmail(), foundUser.getEmail());
    }
}
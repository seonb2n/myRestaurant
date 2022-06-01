package com.example.myrestaurant.application;

import com.example.myrestaurant.domain.user.domain.User;
import com.example.myrestaurant.domain.user.domain.UserCommand;
import com.example.myrestaurant.domain.user.domain.UserInfo;
import com.example.myrestaurant.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserFacade {

    private final UserService userService;

    public UserInfo.Main registerUser(UserCommand.UserRegisterCommand userRegisterCommand) {
        return userService.registerUser(userRegisterCommand);
    }

    public UserInfo.Main logInUser(UserCommand.UserLoginCommand userLoginCommand) {
        return userService.logInUser(userLoginCommand);
    }

    public User getUserWithUserToken(String userToken) {
        return userService.getUserWithUserToken(userToken);
    }

}


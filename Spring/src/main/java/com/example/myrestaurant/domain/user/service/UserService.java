package com.example.myrestaurant.domain.user.service;

import com.example.myrestaurant.domain.user.domain.UserCommand;
import com.example.myrestaurant.domain.user.domain.UserInfo;

public interface UserService {

    UserInfo.Main registerUser(UserCommand.UserRegisterCommand userRegisterCommand);

    UserInfo.Main logInUser(UserCommand.UserLoginCommand userLoginCommand);

}

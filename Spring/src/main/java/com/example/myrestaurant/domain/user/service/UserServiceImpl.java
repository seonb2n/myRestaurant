package com.example.myrestaurant.domain.user.service;

import com.example.myrestaurant.domain.user.domain.User;
import com.example.myrestaurant.domain.user.domain.UserCommand;
import com.example.myrestaurant.domain.user.domain.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserStore userStore;
    private final UserReader userReader;

    @Override
    public UserInfo.Main registerUser(UserCommand.UserRegisterCommand userRegisterCommand) {
        var initUser = userRegisterCommand.toEntity();
        User user = userStore.store(initUser);
        return new UserInfo.Main(user);
    }

    @Override
    public UserInfo.Main logInUser(UserCommand.UserLoginCommand userLoginCommand) {
        var user = userReader.logInWithUserEmailAndPassword(userLoginCommand.getEmail(), userLoginCommand.getPassword());
        return new UserInfo.Main(user);
    }
}

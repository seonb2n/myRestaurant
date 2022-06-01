package com.example.myrestaurant.domain.user.service;

import com.example.myrestaurant.domain.user.domain.User;
import com.example.myrestaurant.domain.user.domain.UserCommand;
import com.example.myrestaurant.domain.user.domain.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserStore userStore;
    private final UserReader userReader;

    @Override
    @Transactional
    public UserInfo.Main registerUser(UserCommand.UserRegisterCommand userRegisterCommand) {
        var initUser = userRegisterCommand.toEntity();
        User user = userStore.store(initUser);
        return new UserInfo.Main(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserInfo.Main logInUser(UserCommand.UserLoginCommand userLoginCommand) {
        var user = userReader.logInWithUserEmailAndPassword(userLoginCommand.getEmail(), userLoginCommand.getPassword());
        return new UserInfo.Main(user);
    }

    @Override
    public User getUserWithUserToken(String userToken) {
        return userReader.getUserWithUserToken(userToken);
    }
}

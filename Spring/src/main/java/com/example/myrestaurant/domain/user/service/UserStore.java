package com.example.myrestaurant.domain.user.service;

import com.example.myrestaurant.domain.user.domain.User;

public interface UserStore {

    User store(User user);

    User registerUser(User initUser);
}

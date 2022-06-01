package com.example.myrestaurant.domain.user.service;

import com.example.myrestaurant.domain.user.domain.User;

public interface UserReader {

    User getUserWithUserEmail(String userEmail);

    User logInWithUserEmailAndPassword(String userEmail, String userPassword);

    User getUserWithUserToken(String userToken);
}

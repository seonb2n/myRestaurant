package com.example.myrestaurant.config;

import lombok.Data;

@Data
public class UserLoginForm {

    private String email;
    private String password;
    private String refreshToken;

}

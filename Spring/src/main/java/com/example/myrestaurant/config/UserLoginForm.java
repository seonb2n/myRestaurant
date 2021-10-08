package com.example.myrestaurant.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginForm {

    private String email;
    private String password;
    private String refreshToken;

}

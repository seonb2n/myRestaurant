package com.example.myrestaurant.domain.user.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

public class UserCommand {

    @Getter
    @Builder
    @ToString
    static class UserRegisterCommand {
        private String email;
        private String password;
        private String nickName;

        public User toEntity() {
            return User.builder()
                    .email(email)
                    .password(password)
                    .nickName(nickName)
                    .build();
        }
    }

    @Getter
    @Builder
    @ToString
    static class UserLoginCommand {
        private String email;
        private String password;
    }

}

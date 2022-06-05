package com.example.myrestaurant.interfaces.user;

import com.example.myrestaurant.domain.restaurant.domain.Restaurant;
import com.example.myrestaurant.domain.user.domain.UserInfo;
import com.example.myrestaurant.interfaces.restaurant.RestaurantDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import java.util.List;

public class UserDto {

    @Getter
    @Setter
    @ToString
    @Builder
    public static class RegisterUserRequest {
        @NotEmpty(message = "사용자 이메일은 필수값입니다.")
        private String email;
        @NotEmpty(message = "사용자 비밀번호는 필수값입니다.")
        private String password;
        @NotEmpty(message = "사용자 닉네임은 필수값입니다.")
        private String nickName;
    }

    @Getter
    @Setter
    @ToString
    public static class RegisterResponse {
        private final String userToken;
        private final String email;
        private final String password;
        private final String nickName;
        private final List<Restaurant> restaurantList;

        public RegisterResponse(UserInfo.Main userInfo) {
            this.userToken = userInfo.getUserToken();
            this.email = userInfo.getEmail();
            this.password = userInfo.getPassword();
            this.nickName = userInfo.getNickName();
            this.restaurantList = userInfo.getRestaurantList();
        }
    }

    @Getter
    @Setter
    @ToString
    public static class LoginUserRequest {
        @NotEmpty(message = "사용자 이메일은 필수값입니다.")
        private String email;
        @NotEmpty(message = "사용자 비밀번호는 필수값입니다.")
        private String password;
    }

    @Getter
    @Setter
    @ToString
    public static class LoginResponse {
        private final String userToken;
        private final String nickName;
        private final String email;
        private final String password;
        private final List<Restaurant> restaurantList;

        public LoginResponse(UserInfo.Main userInfo) {
            this.userToken = userInfo.getUserToken();
            this.nickName = userInfo.getNickName();
            this.restaurantList = userInfo.getRestaurantList();
            this.email = userInfo.getEmail();
            this.password = userInfo.getPassword();
        }
    }

    @Getter
    @Setter
    public static class UpdateUserRequest {
        private String userToken;
        private List<RestaurantDto.RestaurantUpdateDto> restaurantUpdateDtoList;
    }


}

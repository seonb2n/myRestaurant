package com.example.myrestaurant.domain.user.domain;

import com.example.myrestaurant.domain.restaurant.domain.Restaurant;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

public class UserInfo {

    @Getter
    @ToString
    public static class Main {
        private final String userToken;
        private final String email;
        private final String password;
        private final String nickName;
        private final List<Restaurant> restaurantList;

        public Main(User user) {
            this.userToken = user.getUserToken();
            this.email = user.getEmail();
            this.nickName = user.getNickName();
            this.password = user.getPassword();
            this.restaurantList = user.getRestaurantList();
        }
    }

}

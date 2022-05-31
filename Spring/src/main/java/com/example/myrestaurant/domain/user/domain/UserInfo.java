package com.example.myrestaurant.domain.user.domain;

import com.example.myrestaurant.domain.restaurant.domain.Restaurant;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

public class UserInfo {

    @Getter
    @ToString
    public static class Main {
        private String email;
        private String password;
        private String nickName;
        private List<Restaurant> restaurantList;

        public Main(User user) {
            this.email = user.getEmail();
            this.nickName = user.getNickName();
            this.password = user.getPassword();
            this.restaurantList = user.getRestaurantList();
        }
    }

}

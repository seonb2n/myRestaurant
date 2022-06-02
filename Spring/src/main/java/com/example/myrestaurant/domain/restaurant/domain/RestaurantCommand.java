package com.example.myrestaurant.domain.restaurant.domain;

import com.example.myrestaurant.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

public class RestaurantCommand {

    @Getter
    @ToString
    @Builder
    public static class RegisterRestaurantCommand {
        private String name;
        private String location;
        private String link;
        private String category;

        public Restaurant toEntity(User user) {
            return Restaurant.builder()
                    .name(name)
                    .link(link)
                    .location(location)
                    .category(category)
                    .user(user)
                    .build();

        }
    }

    @Getter
    @ToString
    @AllArgsConstructor
    public static class DeleteRestaurantCommand {
        private String name;
        private User user;
    }

    public static List<Restaurant> toRestaurantList(User user, List<RestaurantCommand.RegisterRestaurantCommand> restaurantCommandList) {
        List<Restaurant> restaurantList = new ArrayList<>();
        restaurantCommandList.forEach(rest -> {
            restaurantList.add(rest.toEntity(user));
        });
        return restaurantList;
    }

}

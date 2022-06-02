package com.example.myrestaurant.domain.restaurant.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

public class RestaurantInfo {

    @Getter
    @ToString
    public static class Main {
        private final String restaurantToken;
        private final String name;
        private final String location;
        private final String link;
        private final String category;

        public Main(Restaurant restaurant) {
            this.restaurantToken = restaurant.getRestaurantToken();
            this.name = restaurant.getName();
            this.location = restaurant.getLocation();
            this.link = restaurant.getLink();
            this.category = restaurant.getCategory();
        }
    }

    public static List<RestaurantInfo.Main> toRestaurantInfo(List<Restaurant> restaurantList) {
        List<RestaurantInfo.Main> restaurantInfoList = new ArrayList<>();
        restaurantList.forEach(restaurant -> {
            restaurantInfoList.add(new Main(restaurant));
        });
        return restaurantInfoList;
    }
}

package com.example.myrestaurant.domain.restaurant.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

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
}

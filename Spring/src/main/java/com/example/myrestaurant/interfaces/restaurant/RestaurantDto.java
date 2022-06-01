package com.example.myrestaurant.interfaces.restaurant;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class RestaurantDto {

    @Getter
    @Setter
    @ToString
    public static class RestaurantUpdateDto {
        private String name;
        private String location;
        private String link;
        private String category;
    }

}

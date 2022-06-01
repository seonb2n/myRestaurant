package com.example.myrestaurant.domain.restaurant.service;

import com.example.myrestaurant.domain.restaurant.domain.Restaurant;
import com.example.myrestaurant.domain.user.domain.User;

import java.util.List;

public interface RestaurantReader {

    Restaurant getRestaurantByNameAndUser(String name, User user);

    List<Restaurant> getRestaurantByUser(User user);

    List<String> getRestaurantNameByUser(User user);
}

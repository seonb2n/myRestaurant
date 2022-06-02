package com.example.myrestaurant.domain.restaurant.service;

import com.example.myrestaurant.domain.restaurant.domain.Restaurant;

import java.util.List;

public interface RestaurantStore {

    public Restaurant store(Restaurant restaurant);

    public List<Restaurant> storeAll(List<Restaurant> restaurantList);
}

package com.example.myrestaurant.domain.restaurant.service;

import com.example.myrestaurant.domain.restaurant.domain.Restaurant;
import com.example.myrestaurant.domain.user.domain.User;

public interface RestaurantRemover {

    void deleteRestaurant(Restaurant restaurant);

    void deleteAllRestaurantWithUser(User user);
}

package com.example.myrestaurant.domain.restaurant.service;

import com.example.myrestaurant.domain.restaurant.domain.RestaurantCommand;
import com.example.myrestaurant.domain.restaurant.domain.RestaurantInfo;
import com.example.myrestaurant.domain.user.domain.User;

import java.util.List;

public interface RestaurantUpdateFactory {

    List<RestaurantInfo.Main> updateRestaurantList(User user, List<RestaurantCommand.RegisterRestaurantCommand> restaurantCommandList);

}

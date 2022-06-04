package com.example.myrestaurant.domain.restaurant.service;

import com.example.myrestaurant.domain.restaurant.domain.Restaurant;
import com.example.myrestaurant.domain.restaurant.domain.RestaurantCommand;
import com.example.myrestaurant.domain.restaurant.domain.RestaurantInfo;
import com.example.myrestaurant.domain.user.domain.User;

import java.util.List;

public interface RestaurantService {

    RestaurantInfo.Main registerRestaurant(User user, RestaurantCommand.RegisterRestaurantCommand registerRestaurantCommand);

    void deleteRestaurant(RestaurantCommand.DeleteRestaurantCommand deleteRestaurantCommand);

    List<String> getRestaurantNameList(User user);

    List<RestaurantInfo.Main> getRestaurantList(User user);

    void deleteAllRestaurantWithUser(User user);

    List<RestaurantInfo.Main> registerAllRestaurant(User user, List<RestaurantCommand.RegisterRestaurantCommand> registerRestaurantCommandList);

    void findBestRestaurant();
}

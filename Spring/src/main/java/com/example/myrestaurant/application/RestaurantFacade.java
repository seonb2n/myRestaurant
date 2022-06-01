package com.example.myrestaurant.application;

import com.example.myrestaurant.domain.restaurant.domain.RestaurantCommand;
import com.example.myrestaurant.domain.restaurant.domain.RestaurantInfo;
import com.example.myrestaurant.domain.restaurant.service.RestaurantService;
import com.example.myrestaurant.domain.restaurant.service.RestaurantUpdateFactory;
import com.example.myrestaurant.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantFacade {

    private final RestaurantUpdateFactory restaurantUpdateFactory;

    public List<RestaurantInfo.Main> update(User user, List<RestaurantCommand.RegisterRestaurantCommand> restaurantCommandList) {
        return restaurantUpdateFactory.updateRestaurantList(user, restaurantCommandList);
    }
}

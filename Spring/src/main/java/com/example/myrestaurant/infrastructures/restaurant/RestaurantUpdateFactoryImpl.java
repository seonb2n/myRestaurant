package com.example.myrestaurant.infrastructures.restaurant;

import com.example.myrestaurant.domain.restaurant.domain.RestaurantCommand;
import com.example.myrestaurant.domain.restaurant.domain.RestaurantInfo;
import com.example.myrestaurant.domain.restaurant.service.RestaurantService;
import com.example.myrestaurant.domain.restaurant.service.RestaurantUpdateFactory;
import com.example.myrestaurant.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RestaurantUpdateFactoryImpl implements RestaurantUpdateFactory {

    private final RestaurantService restaurantService;

    @Override
    @Transactional
    public List<RestaurantInfo.Main> updateRestaurantList(User user, List<RestaurantCommand.RegisterRestaurantCommand> restaurantCommandList) {
        restaurantService.deleteAllRestaurantWithUser(user);
        return restaurantService.registerAllRestaurant(user, restaurantCommandList);
    }
}

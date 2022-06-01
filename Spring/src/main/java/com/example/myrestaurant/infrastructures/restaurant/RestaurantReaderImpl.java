package com.example.myrestaurant.infrastructures.restaurant;

import com.example.myrestaurant.common.exception.RestaurantNameNotFoundException;
import com.example.myrestaurant.common.exception.RestaurantTokenNotFoundException;
import com.example.myrestaurant.domain.restaurant.domain.Restaurant;
import com.example.myrestaurant.domain.restaurant.service.RestaurantReader;
import com.example.myrestaurant.domain.restaurant.service.RestaurantStore;
import com.example.myrestaurant.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class RestaurantReaderImpl implements RestaurantReader {

    private final RestaurantRepository restaurantRepository;

    @Override
    public Restaurant getRestaurantByNameAndUser(String name, User user) {
        return restaurantRepository.findRestaurantByNameAndUser(name, user).orElseThrow(RestaurantNameNotFoundException::new);
    }

    @Override
    public List<Restaurant> getRestaurantByUser(User user) {
        return restaurantRepository.findAllByUser(user);
    }

    @Override
    public List<String> getRestaurantNameByUser(User user) {
        return restaurantRepository.findNameByUser(user);
    }
}

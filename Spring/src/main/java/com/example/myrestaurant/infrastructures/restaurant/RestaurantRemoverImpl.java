package com.example.myrestaurant.infrastructures.restaurant;

import com.example.myrestaurant.common.exception.RestaurantNameNotFoundException;
import com.example.myrestaurant.common.exception.RestaurantTokenNotFoundException;
import com.example.myrestaurant.domain.restaurant.domain.Restaurant;
import com.example.myrestaurant.domain.restaurant.service.RestaurantReader;
import com.example.myrestaurant.domain.restaurant.service.RestaurantRemover;
import com.example.myrestaurant.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RestaurantRemoverImpl implements RestaurantRemover {

    private final RestaurantRepository restaurantRepository;

    @Override
    public void deleteRestaurant(Restaurant restaurant) {
        restaurantRepository.delete(restaurant);
    }

    @Override
    public void deleteAllRestaurantWithUser(User user) {
        restaurantRepository.deleteAllByUser(user);
    }
}

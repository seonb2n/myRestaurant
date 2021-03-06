package com.example.myrestaurant.infrastructures.restaurant;

import com.example.myrestaurant.domain.restaurant.domain.Restaurant;
import com.example.myrestaurant.domain.restaurant.service.RestaurantStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class RestaurantStoreImpl implements RestaurantStore {

    private final RestaurantRepository restaurantRepository;

    @Override
    public Restaurant store(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    @Override
    public List<Restaurant> storeAll(List<Restaurant> restaurantList) {
        return restaurantRepository.saveAll(restaurantList);
    }
}

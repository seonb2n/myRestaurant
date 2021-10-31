package com.example.myrestaurant.dto.restaurant.service;

import com.example.myrestaurant.dto.restaurant.domain.Restaurant;
import com.example.myrestaurant.dto.restaurant.respository.RestaurantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class RestaurantService {

    private final RestaurantRepository repository;

    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.repository = restaurantRepository;
    }

    public Restaurant save(Restaurant restaurant) {
        return repository.save(restaurant);
    }

    public void delete(Restaurant restaurant) {
        if (repository.findById(restaurant.getRestaurantId()).isPresent()) {
            repository.delete(restaurant);
        }
    }

    public Optional<Restaurant> findById(Long id) {
        return repository.findById(id);
    }

    public Optional<Restaurant> findByName(String name) {
        return repository.findRestaurantByName(name);
    }

    public List<Restaurant> saveAll(Set<Restaurant> restaurants) {
        return repository.saveAll(restaurants);
    }

}

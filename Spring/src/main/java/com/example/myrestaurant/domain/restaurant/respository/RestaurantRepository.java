package com.example.myrestaurant.domain.restaurant.respository;

import com.example.myrestaurant.domain.restaurant.domain.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    public Optional<Restaurant> findRestaurantByName(String name);
}

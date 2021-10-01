package com.example.myrestaurant.dto.restaurant.respository;

import com.example.myrestaurant.dto.restaurant.domain.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

}

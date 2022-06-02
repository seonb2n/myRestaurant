package com.example.myrestaurant.infrastructures.restaurant;

import com.example.myrestaurant.domain.restaurant.domain.Restaurant;
import com.example.myrestaurant.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    Optional<Restaurant> findRestaurantByNameAndUser(String name, User user);

    List<Restaurant> findAllByUser(User user);

    @Query("select r.name from Restaurant r where r.user=:user")
    List<String> findNameByUser(User user);

    void deleteByRestaurantToken(String restaurantToken);

    void deleteAllByUser(User user);
}

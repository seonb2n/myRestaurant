package com.example.myrestaurant.domain.restaurant.service;

import com.example.myrestaurant.domain.restaurant.domain.RestaurantCommand;
import com.example.myrestaurant.domain.restaurant.domain.RestaurantInfo;
import com.example.myrestaurant.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService{

    private final RestaurantStore restaurantStore;
    private final RestaurantRemover restaurantRemover;
    private final RestaurantReader restaurantReader;

    @Override
    public RestaurantInfo.Main registerRestaurant(User user, RestaurantCommand.RegisterRestaurantCommand registerRestaurantCommand) {
        var initRestaurant = registerRestaurantCommand.toEntity(user);
        var restaurant  = restaurantStore.store(initRestaurant);
        return new RestaurantInfo.Main(restaurant);
    }

    @Override
    public void deleteRestaurant(RestaurantCommand.DeleteRestaurantCommand deleteRestaurantCommand) {
        var restaurant = restaurantReader.getRestaurantByNameAndUser(deleteRestaurantCommand.getName(), deleteRestaurantCommand.getUser());
        restaurantRemover.deleteRestaurant(restaurant);
    }

    @Override
    public List<String> getRestaurantNameList(User user) {
        return restaurantReader.getRestaurantNameByUser(user);
    }

    @Override
    public List<RestaurantInfo.Main> getRestaurantList(User user) {
        return restaurantReader.getRestaurantByUser(user).stream().map(RestaurantInfo.Main::new).collect(Collectors.toList());
    }
}

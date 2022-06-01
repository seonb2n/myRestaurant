package com.example.myrestaurant.infrastructures.restaurant;

import com.example.myrestaurant.domain.restaurant.domain.RestaurantCommand;
import com.example.myrestaurant.domain.restaurant.domain.RestaurantInfo;
import com.example.myrestaurant.domain.restaurant.service.RestaurantService;
import com.example.myrestaurant.domain.restaurant.service.RestaurantUpdateFactory;
import com.example.myrestaurant.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RestaurantUpdateFactoryImpl implements RestaurantUpdateFactory {

    private final RestaurantService restaurantService;

    @Override
    public List<RestaurantInfo.Main> updateRestaurantList(User user, List<RestaurantCommand.RegisterRestaurantCommand> restaurantCommandList) {
        List<String> restaurantNameList = restaurantService.getRestaurantNameList(user);
        List<String> restaurantLatestNameList = new ArrayList<>();
        //restaurantList 에 없는 객체는 새로 등록해야 한다.
        restaurantCommandList.forEach(rest -> {
            if(!restaurantNameList.contains(rest.getName())) {
                restaurantService.registerRestaurant(user, rest);
                restaurantLatestNameList.add(rest.getName());
            }
        });
        //restaurantList 에만 있는 객체는 제거해야 한다.
        restaurantNameList.forEach(name -> {
            if(!restaurantLatestNameList.contains(name)) {
                restaurantService.deleteRestaurant(new RestaurantCommand.DeleteRestaurantCommand(name, user));
            }
        });
        return restaurantService.getRestaurantList(user);
    }
}

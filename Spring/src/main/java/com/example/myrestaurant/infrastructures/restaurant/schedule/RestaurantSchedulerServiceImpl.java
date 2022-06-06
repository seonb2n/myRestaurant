package com.example.myrestaurant.infrastructures.restaurant.schedule;

import com.example.myrestaurant.domain.restaurant.service.RestaurantSchedulerService;
import com.example.myrestaurant.domain.restaurant.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class RestaurantSchedulerServiceImpl implements RestaurantSchedulerService {

    private final RestaurantService restaurantService;

    @Override
    @Scheduled(initialDelay = 1000 * 30, fixedDelay = 1000L * 60 * 60 * 24)
    public void findBestRestaurant() {
//        log.info("run scheduled service");
        restaurantService.findBestRestaurant();
    }
}

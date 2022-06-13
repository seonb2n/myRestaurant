package com.example.myrestaurant.infrastructures.restaurant;

import com.example.myrestaurant.domain.restaurant.domain.Restaurant;
import com.example.myrestaurant.domain.user.domain.User;
import com.example.myrestaurant.infrastructures.user.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RestaurantRepositoryTest {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    @Transactional
    public void pessimistLockTest() throws Exception {
        //given
        User user = User.builder()
                .email("test1@naver.com")
                .nickName("test-user1")
                .password("1234")
                .build();

        userRepository.save(user);

        Restaurant restaurant = Restaurant.builder()
                .user(user)
                .name("test-rest")
                .build();

        restaurantRepository.save(restaurant);

        //when
        Restaurant foundRestaurant = restaurantRepository.findWithPessimisticLockByName("test-rest")
                .orElseThrow(() -> new RuntimeException("can not found"));
        foundRestaurant.setName("new-test-rest");
        //then
        Assertions.assertEquals("new-test-rest", foundRestaurant.getName());
    }
}
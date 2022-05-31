package com.example.myrestaurant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class MyRestaurantApplication {

    public static void main(String[] args) {

        SpringApplication.run(MyRestaurantApplication.class, args);
    }

}

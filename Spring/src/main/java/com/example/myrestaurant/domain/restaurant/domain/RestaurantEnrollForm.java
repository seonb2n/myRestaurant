package com.example.myrestaurant.domain.restaurant.domain;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantEnrollForm {
    private String name;

    private String location;

    private String link;

    private String category;

    private String userEmail;
}

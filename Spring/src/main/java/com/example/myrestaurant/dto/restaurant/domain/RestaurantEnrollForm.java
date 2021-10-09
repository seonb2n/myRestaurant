package com.example.myrestaurant.dto.restaurant.domain;

import com.example.myrestaurant.dto.user.domain.User;
import lombok.*;

import javax.persistence.ManyToOne;

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

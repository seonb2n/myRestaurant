package com.example.myrestaurant.domain.restaurant.domain;

import com.example.myrestaurant.domain.user.domain.User;
import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long restaurantId;

    private String name;

    private String location;

    private String link;

    private String category;

    @ManyToOne
    @ToString.Exclude
    private User user;
}

package com.example.myrestaurant.domain.restaurant.domain;

import com.example.myrestaurant.domain.user.domain.User;
import com.example.myrestaurant.common.util.TokenGenerator;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Restaurant {
    private static final String PREFIX_RESTAURANT = "rest_";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long restaurantId;

    private String restaurantToken;

    private String name;
    private String location;
    private String link;
    private String category;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "userId")
    private User user;

    @Builder
    public Restaurant(String name, String location, String link, String category, User user) {
        this.restaurantToken = TokenGenerator.randomCharacterWithPrefix(PREFIX_RESTAURANT);
        this.location = location;
        this.name = name;
        this.link = link;
        this.category = category;
        this.user = user;
    }
}

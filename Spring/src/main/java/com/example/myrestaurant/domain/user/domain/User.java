package com.example.myrestaurant.domain.user.domain;

import com.example.myrestaurant.domain.BaseEntity;
import com.example.myrestaurant.domain.restaurant.domain.Restaurant;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Getter
@Table(name="user")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Restaurant> restaurantList = new ArrayList<>();

    private String email;

    private String password;

    private String nickName;

    private boolean enabled;

    public void addRestaurant(Restaurant... restaurants) {
        Collections.addAll(this.restaurantList, restaurants);
    }
}

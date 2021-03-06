package com.example.myrestaurant.domain.user.domain;

import com.example.myrestaurant.domain.BaseEntity;
import com.example.myrestaurant.domain.restaurant.domain.Restaurant;
import com.example.myrestaurant.common.util.TokenGenerator;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Table(name="user", indexes = {@Index(name = "user_token_index", columnList = "user_token")})
public class User extends BaseEntity {
    private static final String PREFIX_USER = "user_";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "user_token")
    private String userToken;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user")
    @JsonManagedReference
    private List<Restaurant> restaurantList = new ArrayList<>();

    private String email;

    private String password;

    @Column(name = "user_nickname")
    private String nickName;

    @Builder
    public User(String email, String password, String nickName) {
        this.userToken = TokenGenerator.randomCharacterWithPrefix(PREFIX_USER);
        this.email = email;
        this.password = password;
        this.nickName = nickName;
    }

    public void addRestaurant(Restaurant... restaurants) {
        Collections.addAll(this.restaurantList, restaurants);
    }
}

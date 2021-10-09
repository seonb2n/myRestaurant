package com.example.myrestaurant.support;

import com.example.myrestaurant.dto.Restaurant;

public class MyData {
    public Restaurant restaurant;
    public String link;
    public int img;

    public MyData(Restaurant restaurant, int img, String link){
            this.restaurant = restaurant;
            this.img = img;
            this.link = link;
    }

}

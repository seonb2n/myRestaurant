package com.example.myrestaurant.infrastructures.restaurant.schedule;

public class BestRestaurant implements Comparable<BestRestaurant>{
    String restaurantName;
    int restaurantNumber;

    public BestRestaurant(String restaurantName, int restaurantNumber) {
        this.restaurantName = restaurantName;
        this.restaurantNumber = restaurantNumber;
    }

    @Override
    public int compareTo(BestRestaurant o) {
        return o.restaurantNumber - restaurantNumber;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public int getRestaurantNumber() {
        return restaurantNumber;
    }
}

package com.example.myrestaurant.dto.restaurant;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.myrestaurant.dto.Restaurant;

import java.io.Serializable;

@Entity(tableName = "Restaurant")
public class RestaurantEntity implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;
    private String location;
    private String link;
    private String category;

    public RestaurantEntity() {}
    public RestaurantEntity(Restaurant restaurant) {
        this.name = restaurant.getName();
        this.location = restaurant.getLocation();
        this.link = restaurant.getLink();
        this.category = restaurant.getCategory();
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getLink() {
        return link;
    }

    public String getCategory() {
        return category;
    }
}

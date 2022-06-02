package com.example.myrestaurant.dto.restaurant.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myrestaurant.dto.restaurant.RestaurantEntity;

import java.util.List;

@Dao
public interface RestaurantDao {
    @Insert
    void insert(RestaurantEntity restaurantEntity);

    @Insert
    void insertAll(List<RestaurantEntity> restaurantEntities);

    @Update
    void update(RestaurantEntity restaurantEntity);

    @Delete
    void delete(RestaurantEntity restaurantEntity);

    @Query("SELECT * FROM RESTAURANT")
    List<RestaurantEntity> getAll();
}

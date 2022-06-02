package com.example.myrestaurant.dto.restaurant.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.myrestaurant.dto.restaurant.RestaurantEntity;
import com.example.myrestaurant.dto.restaurant.dao.RestaurantDao;

@Database(entities = RestaurantEntity.class, version = 1)
public abstract class RestaurantDatabase extends RoomDatabase {

    private static RestaurantDatabase INSTANCE = null;

    public abstract RestaurantDao restaurantDao();

    public synchronized static RestaurantDatabase getInstance(Context context) {
        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), RestaurantDatabase.class, "restaurant.db").build();
        }

        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}

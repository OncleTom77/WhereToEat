package com.esgi.androidproject.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.esgi.androidproject.model.Meal;
import com.esgi.androidproject.model.Restaurant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thomasfouan on 24/01/2017.
 */
public class DAORestaurant extends DAOBase {

    public DAORestaurant(Context context) {
        super(context);
    }

    public long insertRestaurant(Restaurant restaurant) {

        ContentValues values = new ContentValues();
        values.put(Restaurant.NAME, restaurant.getName());
        values.put(Restaurant.LONGITUDE, restaurant.getLongitude());
        values.put(Restaurant.LATITUDE, restaurant.getLatitude());

        long id = db.insert(Restaurant.TABLE_NAME, null, values);

        if(id == -1) {
            // Erreur lors de l'insertion !!!!!!!
        }

        return id;
    }

    public int deleteRestaurant(Restaurant restaurant) {

        long id = restaurant.getId();
        int nbDeleted = db.delete(Restaurant.TABLE_NAME, Restaurant.ID + " = ?", new String[]{Long.toString(id)});

        if(nbDeleted != 1) {
            // Attention !!
        }

        return nbDeleted;
    }

    public List<Restaurant> getRestaurants() {

        List<Restaurant> result = new ArrayList<>();
        DAOMeal mealDBHelper = new DAOMeal(db);

        // SELECT
        Cursor cursor = db.query(Restaurant.TABLE_NAME, null, null, null, null, null, null);

        while(cursor.moveToNext()) {
            Restaurant restaurant = new Restaurant();

            restaurant.setId(cursor.getInt(cursor.getColumnIndex(Restaurant.ID)));
            restaurant.setName(cursor.getString(cursor.getColumnIndex(Restaurant.NAME)));
            restaurant.setLongitude(cursor.getDouble(cursor.getColumnIndex(Restaurant.LONGITUDE)));
            restaurant.setLatitude(cursor.getDouble(cursor.getColumnIndex(Restaurant.LATITUDE)));

            // Get all of the meals eaten in this restaurant by the user
            List<Meal> meals = mealDBHelper.getRestaurantMeals(restaurant.getId());
            restaurant.setMeals(meals);

            result.add(restaurant);
        }

        cursor.close();
        return result;
    }
}

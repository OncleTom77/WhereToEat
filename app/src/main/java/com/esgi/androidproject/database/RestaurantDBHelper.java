package com.esgi.androidproject.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.esgi.androidproject.model.Meal;
import com.esgi.androidproject.model.Restaurant;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by thomasfouan on 24/01/2017.
 */
public class RestaurantDBHelper {

    private SQLiteDatabase db;

    public RestaurantDBHelper(DatabaseHelper dbHelper, Context context) {
        try {
            db = dbHelper.getWritableDatabase();
        } catch (SQLiteException e) {
            db = context.openOrCreateDatabase(DatabaseHelper.DB_NAME, MODE_PRIVATE, null);
        }
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
        MealDBHelper mealDBHelper = new MealDBHelper(db);

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

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
        values.put(Restaurant.MARK, restaurant.getMark());
        values.put(Restaurant.LONGITUDE, restaurant.getLongitude());
        values.put(Restaurant.LATITUDE, restaurant.getLatitude());

        long id = db.insert(Restaurant.TABLE_NAME, null, values);

        if(id == -1) {
            System.out.println("ERREUR LORS DE L'INSERTION DU RESTAURANT");
        }

        return id;
    }

    public int deleteRestaurant(Restaurant restaurant) {

        long id = restaurant.getId();
        int nbDeleted = db.delete(Restaurant.TABLE_NAME, Restaurant.ID + " = ?", new String[]{Long.toString(id)});

        if(nbDeleted != 1) {
            System.out.println("ERREUR LORS DE LA SUPPRESSION DU RESTAURANT");
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

            // Set the mark of the restaurant by getting an average of the meal's mark of the restaurant
            restaurant.setMark(getMarkFromMeals(meals));

            result.add(restaurant);
        }

        cursor.close();

        return result;
    }

    public Restaurant getRestaurant(long id) {

        Restaurant result = null;
        DAOMeal mealDBHelper = new DAOMeal(db);

        Cursor cursor = db.query(Restaurant.TABLE_NAME, null, Restaurant.ID + " = ?", new String[] {Long.toString(id)}, null, null, null);

        if(cursor.moveToNext()) {
            result = new Restaurant();

            result.setId(cursor.getInt(cursor.getColumnIndex(Restaurant.ID)));
            result.setName(cursor.getString(cursor.getColumnIndex(Restaurant.NAME)));
            result.setLongitude(cursor.getDouble(cursor.getColumnIndex(Restaurant.LONGITUDE)));
            result.setLatitude(cursor.getDouble(cursor.getColumnIndex(Restaurant.LATITUDE)));

            // Get all of the meals eaten in this restaurant by the user
            List<Meal> meals = mealDBHelper.getRestaurantMeals(result.getId());
            result.setMeals(meals);

            // Set the mark of the restaurant by getting an average of the meal's mark of the restaurant
            result.setMark(getMarkFromMeals(meals));
        }

        return result;
    }

    private int getMarkFromMeals(List<Meal> meals) {

        int mark = 0;
        if(meals.size() > 0) {
            for (Meal m : meals) {
                mark += m.getMark();
            }
            mark /= meals.size();
        }

        return mark;
    }
}

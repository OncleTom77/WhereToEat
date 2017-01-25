package com.esgi.androidproject.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.esgi.androidproject.model.Meal;
import com.esgi.androidproject.model.Restaurant;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by thomasfouan on 24/01/2017.
 */
public class DAOMeal extends DAOBase {

    public DAOMeal(Context context) {
        super(context);
    }

    public DAOMeal(SQLiteDatabase db) {
        super(db);
    }

    public long insertMeal(Meal meal) {

        ContentValues values = new ContentValues();
        values.put(Meal.ID_USER, meal.getIdUser());
        values.put(Meal.ID_RESTAURANT, meal.getIdRestaurant());
        values.put(Meal.ID_USER, meal.getIdUser());
        values.put(Meal.NAME, meal.getName());
        values.put(Meal.MARK, meal.getMark());
        values.put(Meal.PRICE, meal.getPrice());
        values.put(Meal.COMMENT, meal.getComment());
        values.put(Meal.DATE, meal.getDate().toString());

        long idNewMeal = db.insert(Meal.TABLE_NAME, null, values);

        if(idNewMeal == -1) {
            // Erreur lors de l'insertion !!!!!!!
        }

        return idNewMeal;
    }

    public int deleteMeal(Meal meal) {

        long id = meal.getId();
        int nbDeleted = db.delete(Meal.TABLE_NAME, Meal.ID + " = ?", new String[]{Long.toString(id)});

        if(nbDeleted != 1) {
            // Attention !!
        }

        return nbDeleted;
    }

    public List<Meal> getRestaurantMeals(long idRestaurant) {

        List<Meal> result = new ArrayList<>();

        // SELECT
        Cursor cursor = db.query(Meal.TABLE_NAME, null, Meal.ID_RESTAURANT + " = ?", new String[]{Long.toString(idRestaurant)}, null, null, null);

        while(cursor.moveToNext()) {
            Meal meal = new Meal();

            meal.setId(cursor.getInt(cursor.getColumnIndex(Meal.ID)));
            meal.setIdRestaurant(idRestaurant);
            meal.setIdUser(cursor.getInt(cursor.getColumnIndex(Meal.ID_USER)));
            meal.setName(cursor.getString(cursor.getColumnIndex(Meal.NAME)));
            meal.setMark(cursor.getInt(cursor.getColumnIndex(Meal.MARK)));
            meal.setPrice(cursor.getDouble(cursor.getColumnIndex(Meal.PRICE)));
            meal.setComment(cursor.getString(cursor.getColumnIndex(Meal.COMMENT)));
            meal.setDate(new Date(cursor.getString(cursor.getColumnIndex(Meal.DATE))));

            result.add(meal);
        }

        cursor.close();
        return result;
    }
}

package com.esgi.androidproject.model;

import android.content.SharedPreferences;
import android.location.Location;
import android.preference.PreferenceManager;

import com.esgi.androidproject.widget.WhereToEatWidget;

import java.io.Serializable;
import java.util.List;

/**
 * Created by thomasfouan on 22/01/2017.
 */

public class Restaurant implements Serializable {

    public static final String TABLE_NAME = "RESTAURANT";
    public static final String ID = "_ID";
    public static final String NAME = "NAME";
    public static final String LONGITUDE = "LONGITUDE";
    public static final String LATITUDE = "LATITUDE";
    public static final String MARK = "MARK";
    public static final String DB_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
            ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            NAME + " VARCHAR, " +
            MARK + " INTEGER, " +
            LONGITUDE + " DOUBLE, " +
            LATITUDE + " DOUBLE" +
            ")";

    /**
     * The ID in the database of the restaurant.
     */
    private long id;

    /**
     * The name of the restaurant.
     */
    private String name;

    /**
     * The mark of the restaurant, independantly of the marks of the meals of the restaurant.
     */
    private int mark;

    /**
     * The longitude of the restaurant.
     */
    private Double longitude;

    /**
     * The latitude of the restaurant.
     */
    private Double latitude;

    /**
     * The list of the meals eaten in this restaurant.
     */
    private List<Meal> meals;

    /**
     * The indicator for displaying distance in miles or not.
     */
    private boolean milesUnit;

    /**
     * The distance between the user and the restaurant.
     */
    private float distanceFromUser;


    public Restaurant() {
        this.distanceFromUser = -1;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMark() {
        return mark;
    }

    public String getStarsMark() {
        String stars = "";

        for (int i = 1; i <= 5; i++) {
            stars += (mark >= i) ? "★" : "☆";
        }

        return stars;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }

    public void setMilesUnit(boolean milesUnit) {
        this.milesUnit = milesUnit;
    }

    public float getDistanceFromUser() {
        return distanceFromUser;
    }

    public void setDistanceFromUser(float distanceFromUser) {
        this.distanceFromUser = distanceFromUser;
    }

    public Location getLocationFromLatLng() {

        Location location = new Location("");
        location.setLatitude(latitude);
        location.setLongitude(longitude);

        return location;
    }

    @Override
    public String toString() {

        String strDistance = WhereToEatWidget.getFormatDistance(distanceFromUser, milesUnit);

        return name + " - " + getStarsMark() + " - " + strDistance;
    }
}

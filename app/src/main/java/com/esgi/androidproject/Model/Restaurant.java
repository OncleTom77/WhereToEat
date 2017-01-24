package com.esgi.androidproject.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thomasfouan on 22/01/2017.
 */

public class Restaurant {

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



    public Restaurant(long id, String name, int mark, Double longitude, Double latitude) {
        this.id = id;
        this.name = name;
        this.mark = mark;
        this.longitude = longitude;
        this.latitude = latitude;
        this.meals = new ArrayList<>();
    }

    public Restaurant() {
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
}

package com.esgi.androidproject;

/**
 * Created by thomasfouan on 22/01/2017.
 */

public class Restaurant {

    private static final String TABLE_NAME = "RESTAURANT";
    private static final String ID = "_ID";
    private static final String NAME = "NAME";
    private static final String LONGITUDE = "LONGITUDE";
    private static final String LATITUDE = "LATITUDE";
    private static final String MARK = "MARK";
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
    private String id;

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



    public Restaurant(String id, String name, int mark, Double longitude, Double latitude) {
        this.id = id;
        this.name = name;
        this.mark = mark;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Restaurant() {
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
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
}

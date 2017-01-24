package com.esgi.androidproject.model;

/**
 * Created by thomasfouan on 22/01/2017.
 */

public class Meal {

    public static final String TABLE_NAME = "MEAL";
    public static final String ID = "_ID";
    public static final String ID_USER = "_ID_USER";
    public static final String ID_RESTAURANT = "_ID_RESTAURANT";
    public static final String NAME = "NAME";
    public static final String MARK = "MARK";
    public static final String PRICE = "PRICE";
    public static final String STARTER = "STARTER";
    public static final String DISH = "DISH";
    public static final String DESSERT = "DESSERT";
    public static final String DRINK = "DRINK";
    public static final String COMMENT = "COMMENT";
    public static final String DB_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
            ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ID_USER + " INTEGER FOREIGN KEY, " +
            ID_RESTAURANT + " INTEGER FOREIGN KEY, " +
            NAME + " VARCHAR, " +
            MARK + " INTEGER, " +
            PRICE + " DOUBLE, " +
            STARTER + " VARCHAR, " +
            DISH + " VARCHAR, " +
            DESSERT + " VARCHAR, " +
            DRINK + " VARCHAR, " +
            COMMENT + " VARCHAR" +
            ")";

    /**
     * The ID in the database of the meal.
     */
    private long id;

    /**
     * The ID in the database of the user who ate the meal.
     */
    private long idUser;

    /**
     * The ID in the database of the restaurant where the user ate the meal.
     */
    private long idRestaurant;

    /**
     * The name of the meal.
     */
    private String name;

    /**
     * The mark of the meal.
     */
    private int mark;

    /**
     * The price of the meal.
     */
    private double price;

    /**
     * The starter of the meal.
     */
    private String starter;

    /**
     * The dish of the meal.
     */
    private String dish;

    /**
     * The dessert of the meal.
     */
    private String dessert;

    /**
     * The drink of the meal.
     */
    private String drink;

    /**
     * The comment of the user about the meal.
     */
    private String comment;



    public Meal(long id, long idUser, long idRestaurant, String name, double price, int mark, String starter, String dish, String dessert, String drink, String comment) {
        this.id = id;
        this.idUser = idUser;
        this.idRestaurant = idRestaurant;
        this.name = name;
        this.price = price;
        this.mark = mark;
        this.starter = starter;
        this.dish = dish;
        this.dessert = dessert;
        this.drink = drink;
        this.comment = comment;
    }

    public Meal() {
    }



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }

    public long getIdRestaurant() {
        return idRestaurant;
    }

    public void setIdRestaurant(long idRestaurant) {
        this.idRestaurant = idRestaurant;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStarter() {
        return starter;
    }

    public void setStarter(String starter) {
        this.starter = starter;
    }

    public String getDish() {
        return dish;
    }

    public void setDish(String dish) {
        this.dish = dish;
    }

    public String getDessert() {
        return dessert;
    }

    public void setDessert(String dessert) {
        this.dessert = dessert;
    }

    public String getDrink() {
        return drink;
    }

    public void setDrink(String drink) {
        this.drink = drink;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}

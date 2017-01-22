package com.esgi.androidproject.model;

/**
 * Created by thomasfouan on 22/01/2017.
 */

public class Meal {

    private static final String TABLE_NAME = "MEAL";
    private static final String ID = "_ID";
    private static final String ID_USER = "_ID_USER";
    private static final String ID_RESTAURANT = "_ID_RESTAURANT";
    private static final String NAME = "NAME";
    private static final String MARK = "MARK";
    private static final String PRICE = "PRICE";
    private static final String STARTER = "STARTER";
    private static final String DISH = "DISH";
    private static final String DESSERT = "DESSERT";
    private static final String DRINK = "DRINK";
    private static final String COMMENT = "COMMENT";
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
    private String id;

    /**
     * The ID in the database of the user who ate the meal.
     */
    private String idUser;

    /**
     * The ID in the database of the restaurant where the user ate the meal.
     */
    private String idRestaurant;

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



    public Meal(String id, String idUser, String idRestaurant, String name, double price, int mark, String starter, String dish, String dessert, String drink, String comment) {
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



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getIdRestaurant() {
        return idRestaurant;
    }

    public void setIdRestaurant(String idRestaurant) {
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

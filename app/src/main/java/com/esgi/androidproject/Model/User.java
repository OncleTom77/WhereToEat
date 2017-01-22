package com.esgi.androidproject.Model;

/**
 * Created by thomasfouan on 22/01/2017.
 */

public class User {

    private static final String TABLE_NAME = "USER";
    private static final String ID = "_ID";
    private static final String NAME = "NAME";
    private static final String FIRST_NAME = "FIRST_NAME";
    private static final String PSEUDO = "PSEUDO";
    public static final String DB_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
            ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            NAME + " VARCHAR, " +
            FIRST_NAME + " VARCHAR, " +
            PSEUDO + " VARCHAR" +
            ")";

    /**
     * The ID in the database of the user.
     */
    private String id;

    /**
     * The name of the user.
     */
    private String name;

    /**
     * The first name of the user.
     */
    private String firstName;

    /**
     * The pseudo of the user.
     */
    private String pseudo;



    public User(String id, String name, String firstName, String pseudo) {
        this.id = id;
        this.name = name;
        this.firstName = firstName;
        this.pseudo = pseudo;
    }

    public User() {
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        return id != null ? id.equals(user.id) : user.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}

package com.esgi.androidproject.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by thomasfouan on 25/01/2017.
 */
public abstract class DAOBase {

    protected SQLiteDatabase db;

    protected DatabaseHelper databaseHelper;

    public DAOBase(Context context) {
        this.databaseHelper = new DatabaseHelper(context);
        open(context);
    }

    public DAOBase(SQLiteDatabase db) {
        this.db = db;
    }

    protected void open(Context context) {
        try {
            db = databaseHelper.getWritableDatabase();
        } catch (SQLiteException e) {
            db = context.openOrCreateDatabase(DatabaseHelper.DB_NAME, MODE_PRIVATE, null);
        }
    }

    public void close() {
        this.db.close();
    }
}

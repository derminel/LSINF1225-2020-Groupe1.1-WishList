
package com.example.secondtest;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.secondtest.DatabaseContract.COLUMN_USERS_ADDRESS;
import static com.example.secondtest.DatabaseContract.COLUMN_USERS_LASTNAME;
import static com.example.secondtest.DatabaseContract.COLUMN_USERS_LOGIN;
import static com.example.secondtest.DatabaseContract.COLUMN_USERS_NAME;
import static com.example.secondtest.DatabaseContract.COLUMN_USERS_PASSWORD;
import static com.example.secondtest.DatabaseContract.COLUMN_USERS_PHOTO; //pas le temps de gerer les photos
import static com.example.secondtest.DatabaseContract.COLUMN_USERS_PREFERENCES;
import static com.example.secondtest.DatabaseContract.TABLE_USERS;


public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "WishlistApp.db";
    private SQLiteDatabase db;//

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DatabaseContract.VERSION);
        this.db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DatabaseContract.SQL_CREATE_CONTENT);
        db.execSQL(DatabaseContract.SQL_CREATE_USERS);
        db.execSQL(DatabaseContract.SQL_CREATE_FRIENDS);
        db.execSQL(DatabaseContract.SQL_CREATE_PRODUCTS);
        db.execSQL(DatabaseContract.SQL_CREATE_LISTS);
        db.execSQL(DatabaseContract.SQL_CREATE_LIKE);
        db.execSQL(DatabaseContract.SQL_CREATE_MODIFIER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DatabaseContract.SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public SQLiteDatabase getDb() {
        return this.db ;
    }

}

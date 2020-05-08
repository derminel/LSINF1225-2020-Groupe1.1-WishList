package com.example.secondtest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;

import java.util.ArrayList;

import static com.example.secondtest.DatabaseContract.COLUMN_FRIENDS_LOGIN1;
import static com.example.secondtest.DatabaseContract.COLUMN_FRIENDS_LOGIN2;
import static com.example.secondtest.DatabaseContract.COLUMN_USERS_ADDRESS;
import static com.example.secondtest.DatabaseContract.COLUMN_USERS_LASTNAME;
import static com.example.secondtest.DatabaseContract.COLUMN_USERS_LOGIN;
import static com.example.secondtest.DatabaseContract.COLUMN_USERS_NAME;
import static com.example.secondtest.DatabaseContract.COLUMN_USERS_PASSWORD;
import static com.example.secondtest.DatabaseContract.COLUMN_USERS_PHOTO;
import static com.example.secondtest.DatabaseContract.COLUMN_USERS_PREFERENCES;
import static com.example.secondtest.DatabaseContract.TABLE_FRIENDS;
import static com.example.secondtest.DatabaseContract.TABLE_USERS;

public class UserDAO {
    private Cursor users;
    private DatabaseHelper dbh ;

    public UserDAO (Context activePage){
        this.dbh = new DatabaseHelper(activePage);
        this.users = dbh.getDb().rawQuery(String.format("SELECT * FROM %s", TABLE_USERS),null);
    }

    public String lineCounter (){
        return String.valueOf(DatabaseUtils.queryNumEntries(this.dbh.getDb(), TABLE_USERS));
    }

    public boolean exist(String login){
        this.users.moveToFirst() ;
        try {
            while (!(this.users.isLast())){
                if (this.users.getString(0).equals(login)){
                    return true;
                }
                this.users.moveToNext();
            }
            return (this.users.getString(0).equals(login));
        }
        catch (Exception e){
            return false;
        }
    }

    public String getPassword(String login){
        this.users.moveToFirst() ;
        try{
            while (!(this.users.isLast())){
                if (this.users.getString(0).equals(login)){
                    return this.users.getString(6) ;
                }
                this.users.moveToNext();
            }
            return this.users.getString(6);
        }
        catch (Exception e){
            return null;
        }
    }

    public boolean addUser(String name, String lastname, String login, String password, byte[] photo, String address, String preferences){
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USERS_NAME, name);
        contentValues.put(COLUMN_USERS_LASTNAME, lastname);
        contentValues.put(COLUMN_USERS_LOGIN, login);
        contentValues.put(COLUMN_USERS_PASSWORD, password);
        contentValues.put(COLUMN_USERS_PHOTO, photo);
        contentValues.put(COLUMN_USERS_ADDRESS, address);
        contentValues.put(COLUMN_USERS_PREFERENCES, preferences);
        long result = this.dbh.getDb().insert(TABLE_USERS,null ,contentValues);
        return result != -1;
    }

    public void setList(String name, String lastname, String login, String password, byte[] photo, String address, String preferences){
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USERS_NAME, name);
        contentValues.put(COLUMN_USERS_LASTNAME, lastname);
        contentValues.put(COLUMN_USERS_LOGIN, login);
        contentValues.put(COLUMN_USERS_PASSWORD, password);
        contentValues.put(COLUMN_USERS_PHOTO, photo);
        contentValues.put(COLUMN_USERS_ADDRESS, address);
        contentValues.put(COLUMN_USERS_PREFERENCES, preferences);
        this.dbh.getDb().update(TABLE_USERS, contentValues, "Login = ?",new String[] {login});
    }

    public Integer deleteUser(String login) {
        return this.dbh.getDb().delete(TABLE_USERS, COLUMN_USERS_LOGIN + " = ?",new String[] {login});
    }

    public ArrayList<String> getUserLoginDb (){
        ArrayList<String> usersLogin = new ArrayList<String>();
        this.users.moveToFirst() ;
        try{
            while (!(this.users.isLast())){
                usersLogin.add(this.users.getString(0));
                this.users.moveToNext();
            }
            usersLogin.add(this.users.getString(0));
            return usersLogin;
        }
        catch (Exception e){
            return usersLogin;
        }
    }

    public Cursor getAllColumn(String login){
        return this.dbh.getDb().rawQuery(String.format("SELECT * FROM %s WHERE %s = ?", TABLE_USERS,
                COLUMN_USERS_LOGIN) ,new String[] {login});
    }

    public boolean updateProfile(String login, String name, String lastname, String address, String preferences){
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USERS_NAME, name);
        contentValues.put(COLUMN_USERS_LASTNAME, lastname);
        contentValues.put(COLUMN_USERS_ADDRESS, address);
        contentValues.put(COLUMN_USERS_PREFERENCES, preferences);
        long result = this.dbh.getDb().update(TABLE_USERS,contentValues,COLUMN_USERS_LOGIN + " = ?",new String[] {login});
        return result != -1;
    }
}



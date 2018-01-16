package com.example.ricardomoreira.passwordgeneratorfinal;

/**
 * Created by RicardoMoreira on 16/01/2018.
 */


import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RicardoMoreira on 26/10/2017.
 */

public class DatabaseAdapter {



    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;
    public DatabaseAdapter(Context context) {
        dbHelper = new DatabaseHelper(context.getApplicationContext());
    }
    public DatabaseAdapter open() {
        database = dbHelper.getWritableDatabase();
        return this;
    }
    public void close() {
        dbHelper.close();
    }



    public long inserir(String oURL, String oUsername,String aPassword) {
        ContentValues values = new ContentValues() ;
        values.put("url", oURL);
        values.put("username", oUsername);
        values.put("password", aPassword);
        return database.insert("passwords", null, values);
    }
    private Cursor obterTodosRegistos() {
        String[] colunas = new String[3];
        colunas[0] = "URL";
        colunas[1] = "username";
        colunas[2] = "password";

        return database.query("passwords", colunas, null, null, null, null, "URL");
    }

    public List<String> obterTodasURLS() {
        ArrayList<String> urls = new ArrayList<String>();
        Cursor cursor = obterTodosRegistos();
        if (cursor.moveToFirst()) {
            do {
                urls.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return urls;
    }

    public Cursor getIDURL() {
        String[] colunas = new String[2];
        colunas[0] = "_id";
        colunas[1] = "URL";
        return database.query("passwords", colunas, null, null, null, null, null, null);
    }



    public Cursor getId(Integer ind){
        Cursor cursor = database.rawQuery(
                "select _id, URL, username, password from passwords where _id=?", new String[] { ind.toString() });

        return cursor;

    }










}
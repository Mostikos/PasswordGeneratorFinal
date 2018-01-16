package com.example.ricardomoreira.passwordgeneratorfinal;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by RicardoMoreira on 15/01/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {



    private static final int VERSION = 1;


    public DatabaseHelper(Context context){
        super(context , "passwords" , null , VERSION);
    }

    @Override
    public void onCreate (SQLiteDatabase db){
        String createTable =
                "CREATE TABLE passwords(_id integer primary key autoincrement, url varchar(100),username varchar(100), password varchar(100) )";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db , int oldVersion , int newVersion){
        db.execSQL("DROP TABLE IF EXISTS passwords");
        onCreate(db);
    }


}

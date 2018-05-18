package com.tech.oma.myshoes.databasehandler;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {

    // http://androidopentutorials.com/android-sqlite-join-multiple-tables-example/

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "myShoesManager";

    // Shoe table name
    private static final String TABLE_SHOES = "shoes";

    // Shoe Table Columns names
    private static final String KEY_OID = "oid";
    private static final String KEY_ID = "id";
    private static final String KEY_TITEL = "titel";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_IMAGEPATH = "imagePath";
    private static final String KEY_ART = "art";
    private static final String KEY_PRICE = "price";
    private static final String DBEXCEPTION = "Database error";

    private static ShoeDBHandlerImpl instance;
    private static SQLiteDatabase readableDatabase;
    private static SQLiteDatabase writableDatabase;



    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

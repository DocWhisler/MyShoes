package com.tech.oma.myshoes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Whisler on 25.02.2018.
 */

public class DataBaseHandlerImpl extends SQLiteOpenHelper implements DataBaseHandler {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "myShoesManager";

    // Shoe table name
    private static final String TABLE_SHOES = "shoes";

    // Shoe Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_TITEL = "titel";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_IMAGEPATH = "imagePath";
    private static final String KEY_ART = "art";
    private static final String KEY_PRICE = "price";
    private static final String DBEXCEPTION = "Database error";

    private static DataBaseHandlerImpl instance;
    private static SQLiteDatabase readableDatabase;
    private static SQLiteDatabase writableDatabase;


    private DataBaseHandlerImpl(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        writableDatabase = this.getWritableDatabase();
        readableDatabase = this.getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_SHOES_TABLE = "CREATE TABLE " + TABLE_SHOES + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_TITEL + " TEXT,"
                + KEY_DESCRIPTION + " TEXT,"
                + KEY_IMAGEPATH + " TEXT,"
                + KEY_ART + " TEXT,"
                + KEY_PRICE + " REAL)";
        db.execSQL(CREATE_SHOES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHOES);

        // Create tables again
        onCreate(db);
    }

    public static synchronized DataBaseHandlerImpl getDbHandlerInstance(Context context) {
        if (instance == null){
            instance = new DataBaseHandlerImpl(context);
        }
        return instance;
    }

    @Override
    public void addShoe(Shoe shoe) {
        // Werte lesen
        ContentValues values = new ContentValues();
        values.put(KEY_ID, shoe.getId());
        values.put(KEY_TITEL, shoe.getTitel());
        values.put(KEY_DESCRIPTION, shoe.getDescription());
        values.put(KEY_IMAGEPATH, shoe.getImagePath());
        values.put(KEY_ART, shoe.getArt());
        values.put(KEY_PRICE, shoe.getImagePath());

        if(!writableDatabase.isOpen()){
            try (SQLiteDatabase db = this.getWritableDatabase()) {
                // Werte einfügen
                db.insert(TABLE_SHOES, null, values);
            }
        }
        else
        {

        }


    }

    @Override
    public Shoe getShoe(int id) {
        Shoe shoe = null;

        try (SQLiteDatabase db = this.getReadableDatabase()) {
            try (Cursor cursor = db.query(TABLE_SHOES,
                    new String[]{KEY_ID, KEY_TITEL, KEY_DESCRIPTION, KEY_IMAGEPATH, KEY_ART, KEY_PRICE},
                    KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null)) {

                if (cursor != null)
                    cursor.moveToFirst();

                shoe = new Shoe(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getDouble(5));
            }
        }
        return shoe;
    }

    @Override
    public ArrayList<Shoe> getAllShoes() {
        ArrayList<Shoe> shoes = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_SHOES;

        if (readableDatabase.isOpen()){
            Cursor cursor = readableDatabase.rawQuery(query, null);

            if(cursor.moveToFirst()) {
                do {
                    Shoe shoe = new Shoe(
                            cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3),
                            cursor.getString(4),
                            cursor.getDouble(5));

                    shoes.add(shoe);
                }while (cursor.moveToNext());
            }
        }
        else {
            Log.e(DBEXCEPTION, "Keine Verbindung zur Dantenbank");
            throw new RuntimeException(DBEXCEPTION + "Keine Verbindung zur Dantenbank");
        }
        return shoes;
    }

    @Override
    public int getShoesCount() {
        Cursor cursor = null;
        try (SQLiteDatabase db = this.getReadableDatabase()) {
            String countQuery = "SELECT * FROM " + TABLE_SHOES;
            cursor = db.rawQuery(countQuery, null);
        }

        if (cursor != null) {
            return cursor.getCount();
        } else {
            return 0;
        }
    }

    @Override
    public void updateShoe(Shoe shoe) {

        try (SQLiteDatabase db = this.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put(KEY_TITEL, shoe.getTitel());
            values.put(KEY_DESCRIPTION, shoe.getDescription());
            values.put(KEY_IMAGEPATH, shoe.getImagePath());
            values.put(KEY_ART, shoe.getArt());
            values.put(KEY_PRICE, shoe.getPrice());
            db.update(TABLE_SHOES, values, KEY_ID + " = ?", new String[]{String.valueOf(shoe.getId())});
        }
    }

    @Override
    public void deleteShoe(Shoe shoe) {
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            db.delete(TABLE_SHOES, KEY_ID + " = ?", new String[]{String.valueOf(shoe.getId())});
        }
    }

    @Override
    public int getMaxId() {
        Cursor cursor = null;
        String query = "SELECT * FROM " + TABLE_SHOES;

        if(readableDatabase.isOpen()){
            cursor = readableDatabase.rawQuery(query, null);
        }
        else{
            Log.e(DBEXCEPTION, "Keine Verbindung zur Dantenbank");
            throw new RuntimeException(DBEXCEPTION + "Keine Verbindung zur Dantenbank");
        }

        if (cursor.moveToFirst()) {
            return cursor.getInt(0);
        } else {
            return 0;
        }
    }
}

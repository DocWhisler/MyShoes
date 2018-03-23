package com.tech.oma.myshoes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Whisler on 25.02.2018.
 */

public class DataBaseHandler extends SQLiteOpenHelper implements DataBaseHandlerImpl{

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

    public DataBaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
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

    @Override
    public void addShoe(Shoe shoe) {

        try (SQLiteDatabase db = this.getWritableDatabase()) {
            // Werte lesen
            ContentValues values = new ContentValues();
            values.put(KEY_TITEL, shoe.getTitel());
            values.put(KEY_DESCRIPTION, shoe.getDescription());
            values.put(KEY_IMAGEPATH, shoe.getImagePath());
            values.put(KEY_ART, shoe.getArt());
            values.put(KEY_PRICE, shoe.getImagePath());

            // Werte einfügen
            db.insert(TABLE_SHOES, null, values);
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
        ArrayList<Shoe> shoes = new ArrayList<Shoe>();
        String query = "SELECT * FROM " + TABLE_SHOES;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

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
}

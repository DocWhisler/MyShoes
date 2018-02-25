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

    // Contacts table name
    private static final String TABLE_SHOES = "shoes";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_TITEL = "titel";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_IMAGEPATH = "imagePath";
    private static final String KEY_art = "art";

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
                + KEY_art + "TEXT)";
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
        SQLiteDatabase db = this.getWritableDatabase();

        // Werte lesen
        ContentValues values = new ContentValues();
        values.put(KEY_TITEL, shoe.getTitel());
        values.put(KEY_DESCRIPTION, shoe.getDescription());
        values.put(KEY_IMAGEPATH, shoe.getImagePath());
        values.put(KEY_art, shoe.getArt());

        // Werte einfügen
        db.insert(TABLE_SHOES, null, values);
        db.close();
    }

    @Override
    public Shoe getShoe(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_SHOES,
                new String[] {KEY_ID, KEY_TITEL, KEY_DESCRIPTION, KEY_IMAGEPATH, KEY_art},
                KEY_ID + "=?", new String[] {String.valueOf(id)}, null, null, null, null);

        if(cursor != null)
            cursor.moveToFirst();

        return new Shoe(Integer.parseInt(cursor.getString(0)),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3),
                            cursor.getString(4));
    }

    @Override
    public ArrayList<Shoe> getAllShoes() {
        ArrayList<Shoe> shoes = new ArrayList<Shoe>();
        String query = "SELECT * FROM " + TABLE_SHOES;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()) {
            do {
                Shoe shoe = new Shoe(Integer.parseInt(cursor.getString(0)),
                                        cursor.getString(1),
                                        cursor.getString(2),
                                        cursor.getString(3),
                                        cursor.getString(4));

                shoes.add(shoe);
            }while (cursor.moveToNext());
        }

        return shoes;
    }

    @Override
    public int getShoesCount() {
        return 0;
    }

    @Override
    public int updateShoe(Shoe shoe) {
        return 0;
    }

    @Override
    public void deleteShoe(Shoe shoe) {

    }
}
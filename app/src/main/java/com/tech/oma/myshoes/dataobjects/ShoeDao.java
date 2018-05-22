package com.tech.oma.myshoes.dataobjects;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.tech.oma.myshoes.databasehandler.ShoeDbDao;

import java.util.ArrayList;
import java.util.Date;

import static com.tech.oma.myshoes.databasehandler.DataBaseHelper.SHOE_CREATED;
import static com.tech.oma.myshoes.databasehandler.DataBaseHelper.SHOE_DESCRIPTION;
import static com.tech.oma.myshoes.databasehandler.DataBaseHelper.SHOE_ID;
import static com.tech.oma.myshoes.databasehandler.DataBaseHelper.SHOE_IMAGEPATH;
import static com.tech.oma.myshoes.databasehandler.DataBaseHelper.SHOE_OID;
import static com.tech.oma.myshoes.databasehandler.DataBaseHelper.SHOE_PRICE;
import static com.tech.oma.myshoes.databasehandler.DataBaseHelper.SHOE_TAG;
import static com.tech.oma.myshoes.databasehandler.DataBaseHelper.SHOE_TITEL;
import static com.tech.oma.myshoes.databasehandler.DataBaseHelper.TABLE_SHOES;

/**
 * Created by Whisler on 25.02.2018.
 */

public class ShoeDao extends ShoeDbDao implements IShoeDao {

    private static final String DBEXCEPTION = "Database error";

    public ShoeDao(Context context) {
        super(context);
    }

    @Override
    public ArrayList<Shoe> getAllShoes() {
        ArrayList<Shoe> shoes = new ArrayList<>();
        String query = "SELECT " +
                      SHOE_OID +
                ", " + SHOE_ID +
                ", " + SHOE_TITEL +
                ", " + SHOE_DESCRIPTION +
                ", " + SHOE_IMAGEPATH +
                ", " + SHOE_TAG +
                ", " + SHOE_PRICE+
                ", " + SHOE_CREATED +
                " FROM " + TABLE_SHOES;

        if (database.isOpen()){
            Cursor cursor = database.rawQuery(query, null);

            if(cursor.moveToFirst()) {
                do {
                    Shoe shoe = new Shoe(
                            cursor.getInt(1),
                            cursor.getString(2),
                            cursor.getString(3),
                            cursor.getString(4),
                            cursor.getString(5),
                            cursor.getDouble(6));

                    shoe.setOid(cursor.getString(0));
                    shoe.setCreated(new Date(cursor.getLong(7)));
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
    public Shoe getShoe(int id) {
        Shoe shoe = null;
        String query = "SELECT " +
                    SHOE_OID +
                ", " + SHOE_ID +
                ", " + SHOE_TITEL +
                ", " + SHOE_DESCRIPTION +
                ", " + SHOE_IMAGEPATH +
                ", " + SHOE_TAG +
                ", " + SHOE_PRICE+
                ", " + SHOE_CREATED +
                " FROM " + TABLE_SHOES +
                " WHERE " + SHOE_ID + "=" + id;

        if (database.isOpen()){
            Cursor cursor = database.rawQuery(query, null);
            if(cursor.moveToFirst()) {
                shoe = new Shoe(
                        cursor.getInt(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getDouble(6));

                shoe.setOid(cursor.getString(0));
                shoe.setCreated(new Date(cursor.getLong(7)));
            }
        }
        else {
            Log.e(DBEXCEPTION, "Keine Verbindung zur Dantenbank");
            throw new RuntimeException(DBEXCEPTION + "Keine Verbindung zur Dantenbank");
        }
        return shoe;
    }

    @Override
    public Shoe createShoe(String titel, String description, String photoPath, String tag, double price) {
       int id = this.getMaxId()+1;
       return new Shoe(id, titel, description, photoPath, tag, price);
    }

    @Override
    public void saveShoe(Shoe shoe) {
        if(database.isOpen()){
            ContentValues values = new ContentValues();
            values.put(SHOE_OID, shoe.getOid());
            values.put(SHOE_ID, shoe.getId());
            values.put(SHOE_TITEL, shoe.getTitel());
            values.put(SHOE_DESCRIPTION, shoe.getDescription());
            values.put(SHOE_IMAGEPATH, shoe.getImagePath());
            values.put(SHOE_TAG, shoe.getTag());
            values.put(SHOE_PRICE, shoe.getPrice());
            values.put(SHOE_CREATED, shoe.getCreated().getTime());

            database.insert(TABLE_SHOES, null, values);
        }
        else{
            Log.e(DBEXCEPTION, "Keine Verbindung zur Dantenbank");
            throw new RuntimeException(DBEXCEPTION + "Keine Verbindung zur Dantenbank");
        }
    }

    @Override
    public void updateShoe(Shoe shoe) {
        if (database.isOpen()) {
            ContentValues values = new ContentValues();
            values.put(SHOE_ID, shoe.getId());
            values.put(SHOE_TITEL, shoe.getTitel());
            values.put(SHOE_DESCRIPTION, shoe.getDescription());
            values.put(SHOE_IMAGEPATH, shoe.getImagePath());
            values.put(SHOE_TAG, shoe.getTag());
            values.put(SHOE_PRICE, shoe.getPrice());

            database.update(TABLE_SHOES, values, SHOE_ID + " = ?", new String[]{String.valueOf(shoe.getId())});
        }
        else {
            Log.e(DBEXCEPTION, "Keine Verbindung zur Dantenbank");
            throw new RuntimeException(DBEXCEPTION + "Keine Verbindung zur Dantenbank");
        }
    }

    @Override
    public void deleteShoe(Shoe shoe) {
        if(database.isOpen()) {
            database.delete(TABLE_SHOES, SHOE_ID + " = ?", new String[]{String.valueOf(shoe.getId())});
        }
        else{
            Log.e(DBEXCEPTION, "Keine Verbindung zur Dantenbank");
            throw new RuntimeException(DBEXCEPTION + "Keine Verbindung zur Dantenbank");
        }
    }

    @Override
    public int getMaxId() {
        Cursor cursor = null;
        String query = "SELECT max(" + SHOE_ID + ") FROM " + TABLE_SHOES;

        if(database.isOpen()){
            cursor = database.rawQuery(query, null);
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

    @Override
    public int getShoesCount() {
        Cursor cursor;
        String countQuery = "SELECT * FROM " + TABLE_SHOES;
        if (database.isOpen()) {
            cursor = database.rawQuery(countQuery, null);
        }
        else{
            Log.e(DBEXCEPTION, "Keine Verbindung zur Dantenbank");
            throw new RuntimeException(DBEXCEPTION + "Keine Verbindung zur Dantenbank");
        }

        if (cursor != null) {
            return cursor.getCount();
        } else {
            return 0;
        }
    }
}

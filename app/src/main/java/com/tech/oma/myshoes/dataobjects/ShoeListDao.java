package com.tech.oma.myshoes.dataobjects;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.tech.oma.myshoes.databasehandler.ShoeDbDao;

import java.util.Date;

import static com.tech.oma.myshoes.databasehandler.DataBaseHelper.LISTS_CREATED;
import static com.tech.oma.myshoes.databasehandler.DataBaseHelper.LISTS_ID;
import static com.tech.oma.myshoes.databasehandler.DataBaseHelper.LISTS_NAME;
import static com.tech.oma.myshoes.databasehandler.DataBaseHelper.LISTS_OID;
import static com.tech.oma.myshoes.databasehandler.DataBaseHelper.TABLE_LISTS;

public class ShoeListDao extends ShoeDbDao implements IShoeListDao {

    private static final String DBEXCEPTION = "Database error";

    public ShoeListDao(Context context){
        super(context);
    }

    @Override
    public ShoeList getShoeList(int id) {
        ShoeList shoeList = null;
        String query = "SELECT " +
                LISTS_OID +
                ", " + LISTS_ID +
                ", " + LISTS_NAME +
                ", " + LISTS_CREATED +
                " FROM " + TABLE_LISTS +
                " WHERE " + LISTS_ID + "=" + id;

        if (database.isOpen()){
            Cursor cursor = database.rawQuery(query, null);
            if(cursor.moveToFirst()) {
                shoeList = new ShoeList(
                        cursor.getInt(1),
                        cursor.getString(2));

                shoeList.setOid(cursor.getString(0));
                shoeList.setCreated(new Date(cursor.getLong(7)));
            }
        }
        else {
            Log.e(DBEXCEPTION, "Keine Verbindung zur Dantenbank");
            throw new RuntimeException(DBEXCEPTION + "Keine Verbindung zur Dantenbank");
        }
        return shoeList;
    }

    @Override
    public ShoeList createShoeList(String name) {
        int id = this.getMaxId();
        return new ShoeList(id, name);
    }

    @Override
    public void saveShoeList(ShoeList shoeList) {
        if(database.isOpen()){
            ContentValues values = new ContentValues();
            values.put(LISTS_OID, shoeList.getOid());
            values.put(LISTS_ID, shoeList.getId());
            values.put(LISTS_NAME, shoeList.getName());
            values.put(LISTS_CREATED, shoeList.getCreated().getTime());

            database.insert(TABLE_LISTS, null, values);
        }
        else{
            Log.e(DBEXCEPTION, "Keine Verbindung zur Dantenbank");
            throw new RuntimeException(DBEXCEPTION + "Keine Verbindung zur Dantenbank");
        }
    }

    @Override
    public void updateShoeList(ShoeList shoeList) {
        if(database.isOpen()){
            ContentValues values = new ContentValues();
            values.put(LISTS_OID, shoeList.getOid());
            values.put(LISTS_ID, shoeList.getId());
            values.put(LISTS_NAME, shoeList.getName());
            values.put(LISTS_CREATED, shoeList.getCreated().getTime());

            database.update(TABLE_LISTS, values, LISTS_ID + " = ?", new String[]{String.valueOf(shoeList.getId())});
        }
        else{
            Log.e(DBEXCEPTION, "Keine Verbindung zur Dantenbank");
            throw new RuntimeException(DBEXCEPTION + "Keine Verbindung zur Dantenbank");
        }
    }

    @Override
    public void deleteShoeList(ShoeList shoeList) {
        if(database.isOpen()) {
            database.delete(TABLE_LISTS, LISTS_ID + " = ?", new String[]{String.valueOf(shoeList.getId())});
        }
        else{
            Log.e(DBEXCEPTION, "Keine Verbindung zur Dantenbank");
            throw new RuntimeException(DBEXCEPTION + "Keine Verbindung zur Dantenbank");
        }
    }

    @Override
    public int getMaxId() {
        Cursor cursor;
        String countQuery = "SELECT * FROM " + TABLE_LISTS;
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

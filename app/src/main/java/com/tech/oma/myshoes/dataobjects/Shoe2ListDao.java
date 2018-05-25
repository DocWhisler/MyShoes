package com.tech.oma.myshoes.dataobjects;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.tech.oma.myshoes.databasehandler.Shoe2ListDbDao;

import java.util.ArrayList;
import java.util.Date;

import static com.tech.oma.myshoes.databasehandler.DataBaseHelper.LISTS_OID;
import static com.tech.oma.myshoes.databasehandler.DataBaseHelper.SHOE2LISTS_CREATED;
import static com.tech.oma.myshoes.databasehandler.DataBaseHelper.SHOE2LISTS_LIST;
import static com.tech.oma.myshoes.databasehandler.DataBaseHelper.SHOE2LISTS_OID;
import static com.tech.oma.myshoes.databasehandler.DataBaseHelper.SHOE2LISTS_SHOE;
import static com.tech.oma.myshoes.databasehandler.DataBaseHelper.SHOE_CREATED;
import static com.tech.oma.myshoes.databasehandler.DataBaseHelper.SHOE_DESCRIPTION;
import static com.tech.oma.myshoes.databasehandler.DataBaseHelper.SHOE_ID;
import static com.tech.oma.myshoes.databasehandler.DataBaseHelper.SHOE_IMAGEPATH;
import static com.tech.oma.myshoes.databasehandler.DataBaseHelper.SHOE_OID;
import static com.tech.oma.myshoes.databasehandler.DataBaseHelper.SHOE_PRICE;
import static com.tech.oma.myshoes.databasehandler.DataBaseHelper.SHOE_TAG;
import static com.tech.oma.myshoes.databasehandler.DataBaseHelper.SHOE_TITEL;
import static com.tech.oma.myshoes.databasehandler.DataBaseHelper.TABLE_LISTS;
import static com.tech.oma.myshoes.databasehandler.DataBaseHelper.TABLE_SHOE2LISTS;
import static com.tech.oma.myshoes.databasehandler.DataBaseHelper.TABLE_SHOES;

public class Shoe2ListDao extends Shoe2ListDbDao implements IShoe2ListDao {

    private static final String DBEXCEPTION = "Database error";

    public Shoe2ListDao(Context context) {
        super(context);
    }

    @Override
    public ArrayList<Shoe> getShoes4List(ShoeList list) {
        ArrayList<Shoe> shoes = new ArrayList<>();
        String query = "SELECT " +
                             TABLE_SHOES + "." + SHOE_OID + ", " +
                             TABLE_SHOES + "." + SHOE_ID + ", " +
                             TABLE_SHOES + "." + SHOE_TITEL + ", " +
                             TABLE_SHOES + "." + SHOE_DESCRIPTION + ", " +
                             TABLE_SHOES + "." + SHOE_IMAGEPATH + ", " +
                             TABLE_SHOES + "." + SHOE_TAG + ", " +
                             TABLE_SHOES + "." + SHOE_PRICE+ ", " +
                             TABLE_SHOES + "." + SHOE_CREATED +
                         " FROM " +
                                TABLE_SHOE2LISTS + " shoe2List" +
                         " INNER JOIN " + TABLE_SHOES + " shoe ON " +
                               "shoe2List." + SHOE2LISTS_SHOE + "=" + "shoe." + SHOE_OID +
                         "INNER JOIN " + TABLE_LISTS + " list ON " +
                                "list." + SHOE2LISTS_LIST + "=" + "list." + LISTS_OID +
                         "WHERE " +
                                "shoe2List." + SHOE2LISTS_LIST + "=" + list.getOid();

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
    public Shoe2List createShoe2List(Shoe shoe, ShoeList list) {
        return new Shoe2List(shoe, list);
    }

    @Override
    public void saveShoe2List(Shoe2List shoe2List) {
        if(database.isOpen()){
            ContentValues values = new ContentValues();
            values.put(SHOE2LISTS_OID, shoe2List.getOid());
            values.put(SHOE2LISTS_SHOE, shoe2List.getShoeOid());
            values.put(SHOE2LISTS_LIST, shoe2List.getShoeListOid());
            values.put(SHOE2LISTS_CREATED, shoe2List.getCreated().getTime());

            database.insert(TABLE_SHOE2LISTS, null, values);
        }
        else{
            Log.e(DBEXCEPTION, "Keine Verbindung zur Dantenbank");
            throw new RuntimeException(DBEXCEPTION + "Keine Verbindung zur Dantenbank");
        }
    }

    @Override
    public void updateShoe2List(Shoe2List shoe2List) {
        if(database.isOpen()){
            ContentValues values = new ContentValues();
            values.put(SHOE2LISTS_OID, shoe2List.getOid());
            values.put(SHOE2LISTS_SHOE, shoe2List.getShoeOid());
            values.put(SHOE2LISTS_LIST, shoe2List.getShoeListOid());
            values.put(SHOE2LISTS_CREATED, shoe2List.getCreated().getTime());

            database.update(TABLE_SHOE2LISTS, values, SHOE2LISTS_OID + " = ?", new String[]{String.valueOf(shoe2List.getOid())});
        }
        else{
            Log.e(DBEXCEPTION, "Keine Verbindung zur Dantenbank");
            throw new RuntimeException(DBEXCEPTION + "Keine Verbindung zur Dantenbank");
        }
    }

    @Override
    public void deleteShoe2List(Shoe2List shoe2List) {
        if(database.isOpen()) {
            database.delete(TABLE_SHOE2LISTS, SHOE2LISTS_OID + " = ?", new String[]{String.valueOf(shoe2List.getOid())});
        }
        else{
            Log.e(DBEXCEPTION, "Keine Verbindung zur Dantenbank");
            throw new RuntimeException(DBEXCEPTION + "Keine Verbindung zur Dantenbank");
        }
    }
}

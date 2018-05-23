package com.tech.oma.myshoes.dataobjects;

import android.content.Context;

import com.tech.oma.myshoes.databasehandler.Shoe2ListDbDao;

import java.util.ArrayList;

public class Shoe2ListDao extends Shoe2ListDbDao implements IShoe2ListDao {

    private static final String DBEXCEPTION = "Database error";

    public Shoe2ListDao(Context context) {
        super(context);
    }

    @Override
    public ArrayList<Shoe> getShoes4List(ShoeList list) {
//        ArrayList<Shoe> shoes = new ArrayList<>();
//        String query = "SELECT " +
//                SHOE_OID +
//                ", " + SHOE_ID +
//                ", " + SHOE_TITEL +
//                ", " + SHOE_DESCRIPTION +
//                ", " + SHOE_IMAGEPATH +
//                ", " + SHOE_TAG +
//                ", " + SHOE_PRICE+
//                ", " + SHOE_CREATED +
//                " FROM " + TABLE_SHOES;
//
//        if (database.isOpen()){
//            Cursor cursor = database.rawQuery(query, null);
//
//            if(cursor.moveToFirst()) {
//                do {
//                    Shoe shoe = new Shoe(
//                            cursor.getInt(1),
//                            cursor.getString(2),
//                            cursor.getString(3),
//                            cursor.getString(4),
//                            cursor.getString(5),
//                            cursor.getDouble(6));
//
//                    shoe.setOid(cursor.getString(0));
//                    shoe.setCreated(new Date(cursor.getLong(7)));
//                    shoes.add(shoe);
//                }while (cursor.moveToNext());
//            }
//        }
//        else {
//            Log.e(DBEXCEPTION, "Keine Verbindung zur Dantenbank");
//            throw new RuntimeException(DBEXCEPTION + "Keine Verbindung zur Dantenbank");
//        }
//        return shoes;
        return null;

    }

    @Override
    public Shoe2List createShoe2List(Shoe shoe, ShoeList list) {
        return null;
    }

    @Override
    public void saveShoe2List(ShoeList shoeList) {

    }

    @Override
    public void updateShoeList(Shoe2List shoe2List) {

    }

    @Override
    public void deleteShoe2List(Shoe2List shoe2List) {

    }

    @Override
    public int getMaxId() {
        return 0;
    }
}

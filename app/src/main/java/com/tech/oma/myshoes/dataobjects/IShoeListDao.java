package com.tech.oma.myshoes.dataobjects;

import java.util.ArrayList;

public interface IShoeListDao {

    ShoeList getShoeList(int id);

    ShoeList createShoeList(String name, boolean aktiv);

    ArrayList<ShoeList> getAllLists();

    ArrayList<ShoeList> getAktivLists();

    void saveShoeList(ShoeList shoeList);

    void updateShoeList(ShoeList shoeList);

    void deleteShoeList(ShoeList shoeList);

    int getMaxId();
}

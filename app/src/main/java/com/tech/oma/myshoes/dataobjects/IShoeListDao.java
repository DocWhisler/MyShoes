package com.tech.oma.myshoes.dataobjects;

public interface IShoeListDao {

    ShoeList getShoeList(int id);

    ShoeList createShoeList(String name);

    void saveShoeList(ShoeList shoeList);

    void updateShoeList(ShoeList shoeList);

    void deleteShoeList(ShoeList shoeList);

    int getMaxId();
}

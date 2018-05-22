package com.tech.oma.myshoes.dataobjects;

import java.util.ArrayList;

/**
 * Created by Whisler on 25.02.2018.
 */

public interface IShoeDao {

    ArrayList<Shoe> getAllShoes();

    Shoe getShoe(int id);

    Shoe createShoe(String titel, String description, String photoPath, String tag, double price);

    void saveShoe(Shoe shoe);

    void updateShoe(Shoe shoe);

    void deleteShoe(Shoe shoe);

    int getMaxId();

    int getShoesCount();

}

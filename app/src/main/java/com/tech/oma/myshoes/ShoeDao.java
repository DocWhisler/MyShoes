package com.tech.oma.myshoes;

import java.util.ArrayList;

/**
 * Created by Whisler on 25.02.2018.
 */

public interface ShoeDao {

    ArrayList<Shoe> getShoes();

    Shoe getShoe(int id);

    Shoe createShoe(int id, String titel, String description, String photoPath, String art, double price);

    void saveShoe(Shoe shoe);

    void updateShoe(Shoe shoe);

    void deleteShoe(Shoe shoe);
}

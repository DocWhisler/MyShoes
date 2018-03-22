package com.tech.oma.myshoes;

import java.util.ArrayList;

/**
 * Created by Whisler on 25.02.2018.
 */

public interface DataBaseHandlerImpl {

    // Adding new Shoe
    void addShoe(Shoe shoe);

    // Getting single Shoe
    Shoe getShoe(int id);

    // Getting All Shoes
    ArrayList<Shoe> getAllShoes();

    // Getting Shoe Count
    int getShoesCount();

    // Updating single Shoe
    void updateShoe(Shoe shoe);

    // Deleting single Shoe
    void deleteShoe(Shoe shoe);
}

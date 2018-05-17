package com.tech.oma.myshoes.databasehandler;

import com.tech.oma.myshoes.dataobjects.Shoe;

import java.util.ArrayList;

/**
 * Created by Whisler on 25.02.2018.
 */

public interface ShoeDBHandler {

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

    // Get Shoe-Id from Database
    int getMaxId();
}
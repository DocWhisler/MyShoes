package com.tech.oma.myshoes;

import java.util.ArrayList;

/**
 * Created by Whisler on 25.02.2018.
 */

public interface DataBaseHandlerImpl {

    // Adding new Shoe
    public void addShoe(Shoe shoe);

    // Getting single Shoe
    public Shoe getShoe(int id);

    // Getting All Shoes
    public ArrayList<Shoe> getAllShoes();

    // Getting Shoe Count
    public int getShoesCount();

    // Updating single Shoe
    public int updateShoe(Shoe shoe);

    // Deleting single Shoe
    public void deleteShoe(Shoe shoe);
}

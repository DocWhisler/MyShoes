package com.tech.oma.myshoes;

import java.util.ArrayList;

/**
 * Created by Whisler on 25.02.2018.
 */

public interface ShoeDao {

    public ArrayList<Shoe> getShoes();

    public Shoe getShoe(int id);

    public void createShoe(Shoe shoe);

    public void updateShoe(Shoe shoe);

    public void deleteShoe(Shoe shoe);
}

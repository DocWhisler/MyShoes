package com.tech.oma.myshoes;

import java.util.ArrayList;

/**
 * Created by Whisler on 25.02.2018.
 */

public interface ShoeDao {

    ArrayList<Shoe> getShoes();

    Shoe getShoe(int id);

    void createShoe(Shoe shoe);

    void updateShoe(Shoe shoe);

    void deleteShoe(Shoe shoe);
}

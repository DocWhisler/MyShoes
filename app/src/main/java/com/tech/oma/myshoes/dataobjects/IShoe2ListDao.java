package com.tech.oma.myshoes.dataobjects;

import java.util.ArrayList;

public interface IShoe2ListDao {

    ArrayList<Shoe> getShoes4List(ShoeList list);

    Shoe2List createShoe2List(Shoe shoe, ShoeList list);

    void saveShoe2List(Shoe2List shoe2List);

    void updateShoe2List(Shoe2List shoe2List);

    void deleteShoe2List(Shoe2List shoe2List);
}

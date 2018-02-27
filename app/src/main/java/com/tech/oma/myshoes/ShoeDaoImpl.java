package com.tech.oma.myshoes;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by Whisler on 25.02.2018.
 */

public class ShoeDaoImpl implements ShoeDao {

    private DataBaseHandler dbHandler;

    public ShoeDaoImpl(Context context) {
        this.dbHandler = new DataBaseHandler(context);
    }

    @Override
    public ArrayList<Shoe> getShoes() {
        return this.dbHandler.getAllShoes();
    }

    @Override
    public Shoe getShoe(int id) {
        return this.dbHandler.getShoe(id);
    }

    @Override
    public void createShoe(Shoe shoe) {
        this.dbHandler.addShoe(shoe);
    }

    @Override
    public void updateShoe(Shoe shoe) {
        this.dbHandler.updateShoe(shoe);
    }

    @Override
    public void deleteShoe(Shoe shoe) {
        this.dbHandler.deleteShoe(shoe);
    }
}

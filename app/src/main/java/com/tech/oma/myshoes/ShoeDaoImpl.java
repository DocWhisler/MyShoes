package com.tech.oma.myshoes;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by Whisler on 25.02.2018.
 */

public class ShoeDaoImpl implements ShoeDao {

    private DataBaseHandlerImpl dbHandlerinstance;

    private static ShoeDaoImpl shoeDaoInstance;

    private ShoeDaoImpl(Context context) {
        this.dbHandlerinstance = DataBaseHandlerImpl.getDbHandlerInstance(context);
    }

    public static ShoeDao getShoeDaoInstance(Context context){
        if (shoeDaoInstance == null){
            shoeDaoInstance = new ShoeDaoImpl(context);
        }
        return shoeDaoInstance;
    }

    @Override
    public ArrayList<Shoe> getShoes() {
        return this.dbHandlerinstance.getAllShoes();
    }

    @Override
    public Shoe getShoe(int id) {
        return this.dbHandlerinstance.getShoe(id);
    }

    @Override
    public Shoe createShoe(String titel, String description, String photoPath, String tag, double price) {
       int id = this.getMaxId()+1;
       return new Shoe(id, titel, description, photoPath, tag, price);
    }

    @Override
    public void saveShoe(Shoe shoe) {
        this.dbHandlerinstance.addShoe(shoe);
    }

    @Override
    public void updateShoe(Shoe shoe) {
        this.dbHandlerinstance.updateShoe(shoe);
    }

    @Override
    public void deleteShoe(Shoe shoe) {
        this.dbHandlerinstance.deleteShoe(shoe);
    }

    @Override
    public int getMaxId() {
        return this.dbHandlerinstance.getMaxId();
    }
}

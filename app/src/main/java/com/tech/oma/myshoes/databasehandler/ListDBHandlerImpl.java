package com.tech.oma.myshoes.databasehandler;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.tech.oma.myshoes.dataobjects.List;

public class ListDBHandlerImpl extends SQLiteOpenHelper implements ListDBHandler {



    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void addList(List list) {

    }

    @Override
    public List getListById(int id) {
        return null;
    }

    @Override
    public List getListByName(String name) {
        return null;
    }

    @Override
    public void updateList(List list) {

    }

    @Override
    public void deleteShoe(List list) {

    }

    @Override
    public int getMaxId() {
        return 0;
    }
}

package com.tech.oma.myshoes.databasehandler;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class Shoe2ListDbDao {
    protected SQLiteDatabase database;
    private DataBaseHelper dbHelper;
    private Context mContext;

    public Shoe2ListDbDao(Context context){
        this.mContext = context;
        this.dbHelper = DataBaseHelper.getDbHelperInstance(this.mContext);
        this.open();
    }

    public void open() throws SQLException {
        if(dbHelper == null)
            dbHelper = DataBaseHelper.getDbHelperInstance(this.mContext);
        database = dbHelper.getWritableDatabase();
    }
}

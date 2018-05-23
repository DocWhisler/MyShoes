package com.tech.oma.myshoes.databasehandler;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class ShoeListDbDao {
    protected SQLiteDatabase database;
    private DataBaseHelper dbHelper;
    private Context mContext;

    public ShoeListDbDao(Context context){
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

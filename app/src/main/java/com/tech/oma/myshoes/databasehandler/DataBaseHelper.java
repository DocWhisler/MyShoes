package com.tech.oma.myshoes.databasehandler;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {

    // http://androidopentutorials.com/android-sqlite-join-multiple-tables-example/
    // contentValues.put("yourDateColumn", date.getTime());
    // new Date(cursor.getLong(yourColumnIndex));
    //https://stackoverflow.com/questions/20495083/android-sqlite-store-date-types

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "myShoesManager";

    // Shoe table name
    public static final String TABLE_SHOES = "shoes";
    public static final String TABLE_LISTS = "lists";
    public static final String TABLE_SHOE2LISTS = "shoe2lists";

    // Shoe Table Columns names
    public static final String SHOE_OID = "oid";
    public static final String SHOE_ID = "id";
    public static final String SHOE_TITEL = "titel";
    public static final String SHOE_DESCRIPTION = "description";
    public static final String SHOE_IMAGEPATH = "imagePath";
    public static final String SHOE_TAG = "tag";
    public static final String SHOE_PRICE = "price";
    public static final String SHOE_CREATED = "created";

    // Lists Table
    public static final String LISTS_OID = "oid";
    public static final String LISTS_ID = "id";
    public static final String LISTS_NAME = "name";
    public static final String LISTS_CREATED = "created";

    // Shoe2Lists Table
    public static final String SHOE2LISTS_OID = "oid";
    public static final String SHOE2LISTS_SHOE = "shoe";
    public static final String SHOE2LISTS_LIST = "list";
    public static final String SHOE2LISTS_CREATED = "created";

    // Create Tables
    private static final String CREATE_SHOE_TABLE = "CREATE TABLE " + TABLE_SHOES + "("
            + SHOE_OID + " TEXT PRIMARY KEY,"
            + SHOE_ID + " INTEGER,"
            + SHOE_TITEL + " TEXT,"
            + SHOE_DESCRIPTION + " TEXT,"
            + SHOE_IMAGEPATH + " TEXT,"
            + SHOE_TAG + " TEXT,"
            + SHOE_PRICE + " REAL,"
            + SHOE_CREATED + " INTEGER)";

    private static final String CREATE_LIST_TABLE = "CREATE TABLE " + TABLE_LISTS + "("
            + LISTS_OID + " TEXT PRIMARY KEY,"
            + LISTS_ID + " INTEGER,"
            + LISTS_NAME + " TEXT,"
            + LISTS_CREATED + " INTEGER)";

    private static final String CREATE_SHOE2LIST_TABLE = "CREATE TABLE " + TABLE_SHOE2LISTS + "("
            + SHOE2LISTS_OID + " TEXT PRIMARY KEY,"
            + SHOE2LISTS_SHOE + " TEXT,"
            + SHOE2LISTS_LIST + " TEXT,"
            + SHOE2LISTS_CREATED + " INTEGER)";

    private static DataBaseHelper instance;

    private DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized DataBaseHelper getDbHelperInstance(Context context) {
        if (instance == null)
            instance = new DataBaseHelper(context);
        return instance;
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_SHOE_TABLE);
        db.execSQL(CREATE_LIST_TABLE);
        db.execSQL(CREATE_SHOE2LIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHOES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LISTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHOE2LISTS);

        // Create tables again
        onCreate(db);
    }
}

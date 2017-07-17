package com.example.jonat.beerhaus.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by jonat on 7/2/2017.
 */

public class BeerDBHelper extends SQLiteOpenHelper {
    public static final String LOG_TAG = BeerDBHelper.class.getSimpleName();

    //name & version
    private static final String DATABASE_NAME = "beers.db";
    private static final int DATABASE_VERSION = 12;

    public BeerDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Create the database
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE " +
                BeerContract.BeerEntry.TABLE_BEERS + "(" + BeerContract.BeerEntry._ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                BeerContract.BeerEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                BeerContract.BeerEntry.COLUMN_DESCRIPTION + " TEXT NOT NULL, " +
                BeerContract.BeerEntry.COLUMN_CONTENT + " TEXT NOT NULL, " +
                BeerContract.BeerEntry.COLUMN_PRICE + " TEXT NOT NULL, " +
                BeerContract.BeerEntry.COLUMN_VOLUME + " TEXT NOT NULL, " +
                BeerContract.BeerEntry.COLUMN_VARIETAL + " TEXT NOT NULL, " +
                BeerContract.BeerEntry.COLUMN_UPDATED  +  " TEXT NOT NULL, " +
                BeerContract.BeerEntry.COLUMN_CATEGORY  +  " TEXT NOT NULL, " +
                BeerContract.BeerEntry.COLUMN_PRODUCE +  " TEXT NOT NULL, " +
                BeerContract.BeerEntry.COLUMN_ORIGIN  +  " TEXT NOT NULL, " +
                BeerContract.BeerEntry.COLUMN_STYLE  +  " TEXT NOT NULL, " +
                BeerContract.BeerEntry.COLUMN_IMAGE_THUMBNAIL  + " INTEGER NOT NULL);";

        sqLiteDatabase.execSQL(SQL_CREATE_MOVIE_TABLE);
    }

    // Upgrade database when version is changed.
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.w(LOG_TAG, "Upgrading database from version " + oldVersion + " to " +
                newVersion + ". OLD DATA WILL BE DESTROYED");
        // Drop the table
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + BeerContract.BeerEntry.TABLE_BEERS);
        sqLiteDatabase.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" +
                BeerContract.BeerEntry.TABLE_BEERS + "'");

        // re-create database
        onCreate(sqLiteDatabase);
    }
}


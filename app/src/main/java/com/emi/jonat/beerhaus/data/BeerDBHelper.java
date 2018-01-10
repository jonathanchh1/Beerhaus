package com.emi.jonat.beerhaus.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by jonat on 7/2/2017.
 */

public class BeerDBHelper extends SQLiteOpenHelper {

    static final String DATABASE_NAME = "beers.db";
    private static final String LOG_TAG = BeerDBHelper.class.getSimpleName();
    private static final int DATABASE_VERSION = 3;


    public BeerDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_BEERS_TABLE = "CREATE TABLE " + BeerContract.BeerEntry.TABLE_BEERS + " ( " +
                BeerContract.BeerEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                BeerContract.BeerEntry.COLUMN_BEER_ID + " INTEGER NOT NULL, " +
                BeerContract.BeerEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                BeerContract.BeerEntry.COLUMN_CONTENT + " TEXT NOT NULL, " +
                BeerContract.BeerEntry.COLUMN_DESCRIPTION + " TEXT NOT NULL, " +
                BeerContract.BeerEntry.COLUMN_PRICE + " TEXT NOT NULL, " +
                BeerContract.BeerEntry.COLUMN_ORIGIN  +  " TEXT NOT NULL, " +
                BeerContract.BeerEntry.COLUMN_PRODUCE +  " TEXT NOT NULL, " +
                BeerContract.BeerEntry.COLUMN_UPDATED  +  " TEXT NOT NULL, " +
                BeerContract.BeerEntry.COLUMN_IMAGE_THUMBNAIL  + " TEXT NOT NULL, " +
                BeerContract.BeerEntry.COLUMN_VARIETAL + " TEXT NOT NULL, " +
                BeerContract.BeerEntry.COLUMN_STYLE  +  " TEXT NOT NULL, " +
                BeerContract.BeerEntry.COLUMN_CATEGORY  +  " TEXT NOT NULL, " +
                BeerContract.BeerEntry.COLUMN_VOLUME + " TEXT NOT NULL, " +
                BeerContract.BeerEntry.COLUMN_BACKDROP_THUMBNAIL + " TEXT NOT NULL " + ");";

        //gotta do logging
        Log.d(LOG_TAG, SQL_CREATE_BEERS_TABLE);

        db.execSQL(SQL_CREATE_BEERS_TABLE);

        Log.d(LOG_TAG, "all tables created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //this will be invoked when we will change DATABASE_VERSION aka schema of database.
        // if we upgrade schema user will lost his fav. collection
        //comment this out if you don't want this to happen
        db.execSQL("DROP TABLE IF EXISTS " + BeerContract.BeerEntry.TABLE_BEERS);
        onCreate(db);
    }
}



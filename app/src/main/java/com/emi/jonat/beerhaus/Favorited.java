package com.emi.jonat.beerhaus;

import android.content.Context;
import android.database.Cursor;

import com.emi.jonat.beerhaus.data.BeerContract;


/**
 * Created by jonat on 7/4/2017.
 */

public class Favorited {

    public static int isBeerFavorited(Context context, int id) {
        Cursor cursor = context.getContentResolver().query(
                BeerContract.BeerEntry.CONTENT_URI,
                null,   // projection
                BeerContract.BeerEntry.COLUMN_BEER_ID + " = ?", // selection
                new String[] { Integer.toString(id) },   // selectionArgs
                null    // sort order
        );
        assert cursor != null;
        int numRows = cursor.getCount();
        cursor.close();
        return numRows;
    }
}

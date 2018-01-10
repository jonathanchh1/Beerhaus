package com.emi.jonat.beerhaus.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by jonat on 7/2/2017.
 */
public class BeerContract {
    //it should be unique in system,we use package name because it is unique
    public static final String CONTENT_AUTHORITY = "com.emi.jonat.beerhaus.data";

    //base URI for content provider
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    //URI end points for Content provider
    public static final String PATH_FAV = "beers";


    //for favorites
    public static final class BeerEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAV).build();

        //these are MIME types ,not really but they are similar to MIME types
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FAV;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FAV;

        // Table name
        public static final String TABLE_BEERS = "beer";
        // columns
        public static final String COLUMN_BEER_ID = "id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_CONTENT = "alcohol_content";
        public static final String COLUMN_DESCRIPTION = "tags";
        public static final String COLUMN_PRICE = "price_in_cents";
        public static final String COLUMN_ORIGIN = "origin";
        public static final String COLUMN_PRODUCE = "producer_name";
        public static final String COLUMN_UPDATED= "updated_at";
        public static final String COLUMN_IMAGE_THUMBNAIL = "image_thumb_url";
        public static final String COLUMN_VARIETAL = "varietal";
        public static final String COLUMN_STYLE = "style";
        public static final String COLUMN_CATEGORY = "tertiary_category";
        public static final String COLUMN_VOLUME = "volume_in_milliliters";
        public static final String COLUMN_BACKDROP_THUMBNAIL = "image_url";




        public static final int COL_ID = 0;
        public static final int COL_NAME = 1;
        public static final int COL_CONTENT = 2;
        public static final int COL_DESCRIPTION = 3;
        public static final int COL_PRICE = 4;
        public static final int COL_ORIGIN = 5;
        public static final int COL_PRODUCE = 6;
        public static final int COL_UPDATED = 7;
        public static final int COL_IMAGE_THUMBNAIL = 8;
        public static final int COL_VARIETAL = 9;
        public static final int COL_STYLE = 10;
        public static final int COL_CATEGORY = 11;
        public static final int COL_VOLUME = 12;
        public static final int COL_BACKDROP = 13;



        public static Uri buildBeerUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }

}
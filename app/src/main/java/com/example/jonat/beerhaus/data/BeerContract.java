package com.example.jonat.beerhaus.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by jonat on 7/2/2017.
 */

public class BeerContract{

    public static final String CONTENT_AUTHORITY = "com.example.jonat.beerhaus.data";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);


    public static final class BeerEntry implements BaseColumns {
        // table name
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


        // create content uri
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(TABLE_BEERS).build();
        // create cursor of base type directory for multiple entries
        public static final String CONTENT_DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_BEERS;
        // create cursor of base type item for single entry
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE +"/" + CONTENT_AUTHORITY + "/" + TABLE_BEERS;

        // for building URIs on insertion
        public static Uri buildFlavorsUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static final String[] BEER_COLUMNS = {
                COLUMN_BEER_ID,
                COLUMN_NAME,
                COLUMN_IMAGE_THUMBNAIL,
                COLUMN_VOLUME,
                COLUMN_VARIETAL,
                COLUMN_PRICE,
                COLUMN_CATEGORY,
                COLUMN_CONTENT,
                COLUMN_DESCRIPTION,
                COLUMN_ORIGIN,
                COLUMN_PRODUCE,
                COLUMN_STYLE,
                COLUMN_UPDATED,
        };

        public static final int COL_ID = 0;
        public static final int COL_NAME = 1;
        public static final int COL_CONTENT = 2;
        public static final int COL_IMAGE_THUMBNAIL = 3;
        public static final int COL_VOLUME = 4;
        public static final int COL_PRICE = 5;
        public static final int COL_DESCRIPTION = 6;
        public static final int COL_ORIGIN = 7;
        public static final int COL_PRODUCE = 8;
        public static final int COL_VARIETAL = 9;
        public static final int COL_STYLE = 10;
        public static final int COL_UPDATED = 11;
        public static final int COL_CATEGORY = 12;
    }


}

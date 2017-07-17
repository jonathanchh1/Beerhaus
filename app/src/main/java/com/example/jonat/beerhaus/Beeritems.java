package com.example.jonat.beerhaus;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.jonat.beerhaus.data.BeerContract;

/**
 * Created by jonat on 6/25/2017.
 */

public class Beeritems implements Parcelable {
    private String title;
    private int id;
    private String tags;
    private String price;
    private String origin;
    private String volume;
    private String alcohol_level;
    private String producer;
    private String update;
    private String style;
    private String varietal;
    private String catagory_tertiary;
    private String thumbnail;

    //when we ask for favorite movies we will do that via Cursor
    public Beeritems(Cursor cursor) {
        this.id = cursor.getInt(BeerContract.BeerEntry.COL_ID);
        this.title = cursor.getString(BeerContract.BeerEntry.COL_NAME);
        this.tags = cursor.getString(BeerContract.BeerEntry.COL_DESCRIPTION);
        this.price = cursor.getString(BeerContract.BeerEntry.COL_PRICE);
        this.origin = cursor.getString(BeerContract.BeerEntry.COL_ORIGIN);
        this.volume = cursor.getString(BeerContract.BeerEntry.COL_VOLUME);
        this.alcohol_level = cursor.getString(BeerContract.BeerEntry.COL_CONTENT);
        this.producer = cursor.getString(BeerContract.BeerEntry.COL_PRODUCE);
        this.update = cursor.getString(BeerContract.BeerEntry.COL_UPDATED);
        this.style = cursor.getString(BeerContract.BeerEntry.COL_STYLE);
        this.varietal = cursor.getString(BeerContract.BeerEntry.COL_VARIETAL);
        this.catagory_tertiary = cursor.getString(BeerContract.BeerEntry.COL_CATEGORY);
        this.thumbnail = cursor.getString(BeerContract.BeerEntry.COL_IMAGE_THUMBNAIL);

    }

    protected Beeritems(Parcel in) {
        title = in.readString();
        id = in.readInt();
        tags = in.readString();
        price = in.readString();
        origin = in.readString();
        volume = in.readString();
        alcohol_level = in.readString();
        producer = in.readString();
        update = in.readString();
        style = in.readString();
        varietal = in.readString();
        catagory_tertiary = in.readString();
        thumbnail = in.readString();
    }

    public static final Creator<Beeritems> CREATOR = new Creator<Beeritems>() {
        @Override
        public Beeritems createFromParcel(Parcel in) {
            return new Beeritems(in);
        }

        @Override
        public Beeritems[] newArray(int size) {
            return new Beeritems[size];
        }
    };

    public Beeritems() {

    }


    public int getId() {
        return id;
    }

    public String  getPrice() {
        return price;
    }

    public String  getAlcohol_level() {
        return alcohol_level;
    }

    public String getVolume() {
        return volume;
    }

    public String getCatagory_tertiary() {
        return catagory_tertiary;
    }

    public String getOrigin() {
        return origin;
    }

    public String getProducer() {
        return producer;
    }

    public String getStyle() {
        return style;
    }

    public String getTags() {
        return tags;
    }

    public String getUpdate() {
        return update;
    }

    public String getVarietal() {
        return varietal;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAlcohol_level(String alcohol_level) {
        this.alcohol_level = alcohol_level;
    }

    public void setCatagory_tertiary(String catagory_tertiary) {
        this.catagory_tertiary = catagory_tertiary;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public void setUpdate(String update) {
        this.update = update;
    }

    public void setVarietal(String varietal) {
        this.varietal = varietal;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeInt(id);
        dest.writeString(tags);
        dest.writeString(price);
        dest.writeString(origin);
        dest.writeString(volume);
        dest.writeString(alcohol_level);
        dest.writeString(producer);
        dest.writeString(update);
        dest.writeString(style);
        dest.writeString(varietal);
        dest.writeString(catagory_tertiary);
        dest.writeString(thumbnail);
    }
}

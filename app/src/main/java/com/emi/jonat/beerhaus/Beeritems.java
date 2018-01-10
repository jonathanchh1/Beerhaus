package com.emi.jonat.beerhaus;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.emi.jonat.beerhaus.data.BeerContract;
import com.google.firebase.database.Exclude;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jonat on 6/25/2017.
 */

public class Beeritems implements Parcelable {

    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String title;
    @SerializedName("tags")
    private String tags;
    @SerializedName("regular_price_in_cents")
    private int price;
    @SerializedName("origin")
    private String origin;
    @SerializedName("volume_in_milliliters")
    private int volume;
    @SerializedName("alcohol_content")
    private int alcohol_level;
    @SerializedName("producer_name")
    private String producer;
    @SerializedName("updated_at")
    private String update;
    @SerializedName("style")
    private String style;
    @SerializedName("varietal")
    private String varietal;
    @SerializedName("tertiary_category")
    private String catagory_tertiary;
    @SerializedName("image_thumb_url")
    private String thumbnail;
    @SerializedName("image_url")
    private String backdropImage;




    public Beeritems(Cursor cursor) {
        this.id = cursor.getInt(BeerContract.BeerEntry.COL_ID);
        this.title = cursor.getString(BeerContract.BeerEntry.COL_NAME);
        this.alcohol_level = cursor.getInt(BeerContract.BeerEntry.COL_CONTENT);
        this.tags = cursor.getString(BeerContract.BeerEntry.COL_DESCRIPTION);
        this.price = cursor.getInt(BeerContract.BeerEntry.COL_PRICE);
        this.origin = cursor.getString(BeerContract.BeerEntry.COL_ORIGIN);
        this.producer = cursor.getString(BeerContract.BeerEntry.COL_PRODUCE);
        this.update = cursor.getString(BeerContract.BeerEntry.COL_UPDATED);
        this.thumbnail = cursor.getString(BeerContract.BeerEntry.COL_IMAGE_THUMBNAIL);
        this.varietal = cursor.getString(BeerContract.BeerEntry.COL_VARIETAL);
        this.style = cursor.getString(BeerContract.BeerEntry.COL_STYLE);
        this.catagory_tertiary = cursor.getString(BeerContract.BeerEntry.COL_CATEGORY);
        this.volume = cursor.getInt(BeerContract.BeerEntry.COL_VOLUME);
        this.backdropImage = cursor.getString(BeerContract.BeerEntry.COL_BACKDROP);

    }


    public Beeritems() {

    }


    public void setVolume(int volume) {
        this.volume = volume;
    }

    public void setVarietal(String varietal) {
        this.varietal = varietal;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void setUpdate(String update) {
        this.update = update;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBackdropImage(String backdropImage) {
        this.backdropImage = backdropImage;
    }

    public void setCatagory_tertiary(String catagory_tertiary) {
        this.catagory_tertiary = catagory_tertiary;
    }

    public void setAlcohol_level(int alcohol_level) {
        this.alcohol_level = alcohol_level;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getUpdate() {
        return update;
    }

    public String getTitle() {
        return title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getTags() {
        return tags;
    }

    public String getStyle() {
        return style;
    }

    public String getProducer() {
        return producer;
    }

    public String getOrigin() {
        return origin;
    }

    public String getCatagory_tertiary() {
        return catagory_tertiary;
    }

    public String getBackdropImage() {
        return backdropImage;
    }

    public String getVarietal() {
        return varietal;
    }

    public int getAlcohol_level() {
        return alcohol_level;
    }

    public int getId() {
        return id;
    }

    public int getPrice() {
        return price;
    }

    public int getVolume() {
        return volume;
    }

    protected Beeritems(Parcel in) {
        id = in.readInt();
        title = in.readString();
        tags = in.readString();
        price = in.readInt();
        origin = in.readString();
        volume = in.readInt();
        alcohol_level = in.readInt();
        producer = in.readString();
        update = in.readString();
        style = in.readString();
        varietal = in.readString();
        catagory_tertiary = in.readString();
        thumbnail = in.readString();
        backdropImage = in.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeString(tags);
        parcel.writeInt(price);
        parcel.writeString(origin);
        parcel.writeInt(volume);
        parcel.writeInt(alcohol_level);
        parcel.writeString(producer);
        parcel.writeString(update);
        parcel.writeString(style);
        parcel.writeString(varietal);
        parcel.writeString(catagory_tertiary);
        parcel.writeString(thumbnail);
        parcel.writeString(backdropImage);
    }
}

package com.emi.jonat.beerhaus;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jonat on 12/31/2017.
 */

public class Store implements Parcelable {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String title;
    @SerializedName("address_line_1")
    private String address;
    @SerializedName("city")
    private String city;
    @SerializedName("postal_code")
    private String postal;
    @SerializedName("telephone")
    private String telephone;
    @SerializedName("products_count")
    private int products;
    @SerializedName("inventory_count")
    private int inventory;
    @SerializedName("updated_at")
    private String update;
    @SerializedName("inventory_price_in_cents")
    private int inventory_price;
    @SerializedName("fax")
    private String fax;
    @SerializedName("has_bilingual_services")
    private boolean bilingual_services;
    @SerializedName("has_product_consultant")
    private boolean products_consultant;
    @SerializedName("has_tasting_bar")
    private boolean tastingbar;
    @SerializedName("has_transit_access")
    private boolean hasTransitAcess;


    protected Store(Parcel in) {
        id = in.readInt();
        title = in.readString();
        address = in.readString();
        city = in.readString();
        postal = in.readString();
        telephone = in.readString();
        products = in.readInt();
        inventory = in.readInt();
        update = in.readString();
        inventory_price = in.readInt();
        fax = in.readString();
        bilingual_services = in.readByte() != 0;
        products_consultant = in.readByte() != 0;
        tastingbar = in.readByte() != 0;
        hasTransitAcess = in.readByte() != 0;
    }


    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setProducts(int products) {
        this.products = products;
    }

    public void setPostal(String postal) {
        this.postal = postal;
    }

    public void setInventory_price(int inventory_price) {
        this.inventory_price = inventory_price;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBilingual_services(boolean bilingual_services) {
        this.bilingual_services = bilingual_services;
    }

    public boolean isBilingual_services() {
        return bilingual_services;
    }

    public boolean isHasTransitAcess() {
        return hasTransitAcess;
    }

    public boolean isProducts_consultant() {
        return products_consultant;
    }

    public boolean isTastingbar() {
        return tastingbar;
    }

    public void setUpdate(String update) {
        this.update = update;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public void setHasTransitAcess(boolean hasTransitAcess) {
        this.hasTransitAcess = hasTransitAcess;
    }

    public void setProducts_consultant(boolean products_consultant) {
        this.products_consultant = products_consultant;
    }

    public void setTastingbar(boolean tastingbar) {
        this.tastingbar = tastingbar;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getCity() {
        return city;
    }

    public String getPostal() {
        return postal;
    }

    public String getAddress() {
        return address;
    }

    public int getProducts() {
        return products;
    }

    public String getTitle() {
        return title;
    }

    public int getInventory() {
        return inventory;
    }

    public String getUpdate() {
        return update;
    }

    public int getInventory_price() {
        return inventory_price;
    }

    public int getId() {
        return id;
    }

    public String getFax() {
        return fax;
    }

    public static final Creator<Store> CREATOR = new Creator<Store>() {
        @Override
        public Store createFromParcel(Parcel in) {
            return new Store(in);
        }

        @Override
        public Store[] newArray(int size) {
            return new Store[size];
        }
    };

    public Store() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(address);
        dest.writeString(city);
        dest.writeString(postal);
        dest.writeString(telephone);
        dest.writeInt(products);
        dest.writeInt(inventory);
        dest.writeString(update);
        dest.writeInt(inventory_price);
        dest.writeString(fax);
        dest.writeByte((byte) (bilingual_services ? 1 : 0));
        dest.writeByte((byte) (products_consultant ? 1 : 0));
        dest.writeByte((byte) (tastingbar ? 1 : 0));
        dest.writeByte((byte) (hasTransitAcess ? 1 : 0));
    }
}

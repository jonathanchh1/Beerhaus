package com.emi.jonat.beerhaus.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jonat on 12/28/2017.
 */

public class Pager implements Parcelable {

    @SerializedName("records_per_page")
    private int page;
    @SerializedName("total_record_count")
    private int total_record;
    @SerializedName("current_page_record_count")
    private int currentPage_record;
    @SerializedName("is_first_page")
    private boolean firstpage;
    @SerializedName("is_final_page")
    private boolean isfinal;
    @SerializedName("current_page")
    private int currentPage;
    @SerializedName("current_page_path")
    private String currentpagePath;
    @SerializedName("next_page")
    private int nextpage;
    @SerializedName("next_page_path")
    private String nextPagePath;
    @SerializedName("previous_page")
    private String previousPage;
    @SerializedName("previous_page_path")
    private String previousPagePath;
    @SerializedName("total_pages")
    private int totalpages;
    @SerializedName("total_pages_path")
    private String totalpagesPath;


    public int getTotal_record() {
        return total_record;
    }

    public String getPreviousPagePath() {
        return previousPagePath;
    }

    public String getPreviousPage() {
        return previousPage;
    }

    public int getPage() {
        return page;
    }

    public int getNextpage() {
        return nextpage;
    }

    public int getTotalpages() {
        return totalpages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getCurrentPage_record() {
        return currentPage_record;
    }

    public String getCurrentpagePath() {
        return currentpagePath;
    }

    public String getNextPagePath() {
        return nextPagePath;
    }

    public String getTotalpagesPath() {
        return totalpagesPath;
    }

    public void setTotalpagesPath(String totalpagesPath) {
        this.totalpagesPath = totalpagesPath;
    }

    public void setTotal_record(int total_record) {
        this.total_record = total_record;
    }

    public void setPreviousPagePath(String previousPagePath) {
        this.previousPagePath = previousPagePath;
    }

    public void setPreviousPage(String previousPage) {
        this.previousPage = previousPage;
    }

    public void setFirstpage(boolean firstpage) {
        this.firstpage = firstpage;
    }

    public void setNextpage(int nextpage) {
        this.nextpage = nextpage;
    }

    public void setTotalpages(int totalpages) {
        this.totalpages = totalpages;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setNextPagePath(String nextPagePath) {
        this.nextPagePath = nextPagePath;
    }

    public void setIsfinal(boolean isfinal) {
        this.isfinal = isfinal;
    }

    public void setCurrentpagePath(String currentpagePath) {
        this.currentpagePath = currentpagePath;
    }

    public void setCurrentPage_record(int currentPage_record) {
        this.currentPage_record = currentPage_record;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    protected Pager(Parcel in) {
        page = in.readInt();
        total_record = in.readInt();
        currentPage_record = in.readInt();
        firstpage = in.readByte() != 0;
        isfinal = in.readByte() != 0;
        currentPage = in.readInt();
        currentpagePath = in.readString();
        nextpage = in.readInt();
        nextPagePath = in.readString();
        previousPage = in.readString();
        previousPagePath = in.readString();
        totalpages = in.readInt();
        totalpagesPath = in.readString();
    }

    public static final Creator<Pager> CREATOR = new Creator<Pager>() {
        @Override
        public Pager createFromParcel(Parcel in) {
            return new Pager(in);
        }

        @Override
        public Pager[] newArray(int size) {
            return new Pager[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(page);
        parcel.writeInt(total_record);
        parcel.writeInt(currentPage_record);
        parcel.writeByte((byte) (firstpage ? 1 : 0));
        parcel.writeByte((byte) (isfinal ? 1 : 0));
        parcel.writeInt(currentPage);
        parcel.writeString(currentpagePath);
        parcel.writeInt(nextpage);
        parcel.writeString(nextPagePath);
        parcel.writeString(previousPage);
        parcel.writeString(previousPagePath);
        parcel.writeInt(totalpages);
        parcel.writeString(totalpagesPath);
    }
}

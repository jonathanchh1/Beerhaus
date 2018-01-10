package com.emi.jonat.beerhaus;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by jonat on 10/8/2017.
 */

public class StoresResponse {
    @SerializedName("status")
    private int status;
    @SerializedName("message")
    private String message;
    @SerializedName("result")
    private ArrayList<Store> results;
    @SerializedName("pager")
    private Pager pager;


    public ArrayList<Store> getResults() {
        return results;
    }


    public Pager getPager() {
        return pager;
    }

    public void setPager(Pager pager) {
        this.pager = pager;
    }

    public int getStatus() {
        return status;
    }


    public String getMessage() {
        return message;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public void setResults(ArrayList<Store> results) {
        this.results = results;
    }

}


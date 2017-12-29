package com.example.jonat.beerhaus;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jonat on 10/8/2017.
 */

public class BeerResponse {
    @SerializedName("status")
    private int status;
    @SerializedName("message")
    private String message;
    @SerializedName("result")
    private ArrayList<Beeritems> results;
    @SerializedName("pager")
    private Pager pager;
    @SerializedName("suggestion")
    private String suggestion;


    public ArrayList<Beeritems> getResults() {
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

    public String getSuggestion() {
        return suggestion;
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


    public void setResults(ArrayList<Beeritems> results) {
        this.results = results;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }
}


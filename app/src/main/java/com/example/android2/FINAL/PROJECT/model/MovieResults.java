package com.example.android2.FINAL.PROJECT.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieResults<T> {

    @SerializedName("page")
    private int page;
    @SerializedName("results")
    private List<T> results;

    public MovieResults() {
    }

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }

}

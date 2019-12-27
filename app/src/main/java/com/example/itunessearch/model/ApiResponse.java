package com.example.itunessearch.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiResponse {
    @SerializedName("resultCount")
    @Expose
    private Integer resultCount;
    @SerializedName("results")
    @Expose
    private List<Song> tracks = null;

    public Integer getResultCount() {
        return resultCount;
    }

    public List<Song> getTracks() {
        return tracks;
    }
}

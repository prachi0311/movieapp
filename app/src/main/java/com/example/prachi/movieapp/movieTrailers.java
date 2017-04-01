package com.example.prachi.movieapp;

import com.google.gson.annotations.SerializedName;

/**
 * Created by prachi on 28/3/17.
 */

public class movieTrailers {
    @SerializedName("id")
    String id;
    @SerializedName("key")
    String key;
    @SerializedName("name")
    String name;
    @SerializedName("size")
    int size;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}

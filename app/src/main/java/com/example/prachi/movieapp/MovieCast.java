package com.example.prachi.movieapp;

import com.google.gson.annotations.SerializedName;

/**
 * Created by prachi on 28/3/17.
 */

public class MovieCast {
    @SerializedName("id")
    int id;
    @SerializedName("character")
    String character;
    @SerializedName("name")
    String name;
    @SerializedName("profile_path")
    String castimage;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCastimage() {
        return castimage;
    }

    public void setCastimage(String castimage) {
        this.castimage = castimage;
    }
}

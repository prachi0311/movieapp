package com.example.prachi.movieapp;

import com.google.gson.annotations.SerializedName;

/**
 * Created by prachi on 29/3/17.
 */

public class castMovieListInfo {
    @SerializedName("character")
    String Character;
    @SerializedName("id")
    int movieid;

    public String getCharacter() {
        return Character;
    }

    public void setCharacter(String character) {
        Character = character;
    }

    public int getMovieid() {
        return movieid;
    }

    public void setMovieid(int movieid) {
        this.movieid = movieid;
    }
}

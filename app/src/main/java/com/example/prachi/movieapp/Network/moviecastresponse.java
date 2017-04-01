package com.example.prachi.movieapp.Network;

import com.example.prachi.movieapp.MovieCast;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by prachi on 28/3/17.
 */

public class moviecastresponse {
    ArrayList<MovieCast> cast;

    public ArrayList<MovieCast> getCast() {
        return cast;
    }

    public void setCast(ArrayList<MovieCast> cast) {
        this.cast = cast;
    }
}

package com.example.prachi.movieapp;

import android.content.Intent;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by prachi on 22/3/17.
 */

public class MovieInfo {
   @SerializedName("id")
    int id;
    @SerializedName("original_title")
    String title;
    @SerializedName("release_date")
    String releasedate;
    @SerializedName("overview")
    String overview;
    @SerializedName("vote_average")
    float rating;
    @SerializedName("poster_path")
    String posterpath;
    @SerializedName("original_language")
    String language;
    @SerializedName("backdrop_path")
    String backdrop;
    @SerializedName("genre_ids")
    ArrayList<Integer> genres;

    public String getPosterpath() {
        return posterpath;
    }

    public void setPosterpath(String posterpath) {
        this.posterpath = posterpath;
    }

    public String getReleasedate() {
        return releasedate;
    }

    public void setReleasedate(String releasedate) {
        this.releasedate = releasedate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBackdrop() {
        return backdrop;
    }

    public void setBackdrop(String backdrop) {
        this.backdrop = backdrop;
    }

    public ArrayList<Integer> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<Integer> genres) {
        this.genres = genres;
    }
}

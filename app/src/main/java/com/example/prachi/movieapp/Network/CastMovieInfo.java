package com.example.prachi.movieapp.Network;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by prachi on 29/3/17.
 */

public class CastMovieInfo {
    int runtime;
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
    @SerializedName("genres")
    ArrayList<Genre> genres;

    public class Genre{

        String name;

        public String getName() {
            return name;
        }
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleasedate() {
        return releasedate;
    }

    public void setReleasedate(String releasedate) {
        this.releasedate = releasedate;
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

    public String getPosterpath() {
        return posterpath;
    }

    public void setPosterpath(String posterpath) {
        this.posterpath = posterpath;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getBackdrop() {
        return backdrop;
    }

    public void setBackdrop(String backdrop) {
        this.backdrop = backdrop;
    }

    public ArrayList<Genre> getGenres() {
        return genres;
    }


}

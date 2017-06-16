package com.example.prachi.movieapp.Network;

import com.example.prachi.movieapp.reviewobjecttype;

import java.util.ArrayList;

/**
 * Created by prachi on 14/4/17.
 */

public class ReviewResponse {
    ArrayList<reviewobjecttype> results;

    public ArrayList<reviewobjecttype> getResults() {
        return results;
    }

    public void setResults(ArrayList<reviewobjecttype> results) {
        this.results = results;
    }
}

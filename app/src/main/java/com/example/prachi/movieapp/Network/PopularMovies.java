package com.example.prachi.movieapp.Network;

import com.example.prachi.movieapp.MovieInfo;

import java.util.ArrayList;

/**
 * Created by prachi on 23/3/17.
 */

public class PopularMovies {


        public ArrayList<MovieInfo> results;
        public ArrayList<MovieInfo> getMovieinfo() {
            return results;
        }
        public void setMovieinfo(ArrayList<MovieInfo> movieinfo) {
            this.results = movieinfo;
        }



}

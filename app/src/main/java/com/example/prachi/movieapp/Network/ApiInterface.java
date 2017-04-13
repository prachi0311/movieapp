package com.example.prachi.movieapp.Network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by prachi on 23/3/17.
 */

public interface ApiInterface {

 @GET("movie/popular?api_key=1b54ff0e150f8aa8199d7fac9a3c5751")
 Call<PopularMovies> getpopularmovies();

 @GET("movie/{MOVIE_ID}/videos?api_key=1b54ff0e150f8aa8199d7fac9a3c5751")
 Call<MovieTrailer> getmovieTrailer(@Path("MOVIE_ID") int movie_id);

 @GET("movie/{MOVIE_ID}/casts?api_key=1b54ff0e150f8aa8199d7fac9a3c5751")
 Call<moviecastresponse> getmoviecast(@Path("MOVIE_ID") int movie_id);

 @GET("movie/{MOVIE_ID}?api_key=1b54ff0e150f8aa8199d7fac9a3c5751")
 Call<CastMovieInfo> getmovieinfo(@Path("MOVIE_ID") int movie_id);

 @GET("person/{person_id}/movie_credits?api_key=1b54ff0e150f8aa8199d7fac9a3c5751")
 Call<CastmovieList> getcastmovielist(@Path("person_id") int person_id);

 @GET("movie/top_rated?api_key=1b54ff0e150f8aa8199d7fac9a3c5751")
 Call<PopularMovies> getTopRatedMovies();

 @GET("movie/upcoming?api_key=1b54ff0e150f8aa8199d7fac9a3c5751")
 Call<PopularMovies> getUpcomingMovies();

 @GET("genre/{genre_id}/movies?api_key=1b54ff0e150f8aa8199d7fac9a3c5751")
 Call<PopularMovies> getGenreMovies(@Path("genre_id") int genreid);

 @GET("search/movie?api_key=1b54ff0e150f8aa8199d7fac9a3c5751")
 Call<PopularMovies> getSearchedMovies(@Query("query") String searchesMovie);








}

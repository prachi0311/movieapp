package com.example.prachi.movieapp.Network;

import com.example.prachi.movieapp.RequestValidation;
import com.example.prachi.movieapp.ratemovieresponse;
import com.example.prachi.movieapp.ratingrequestbody;
import com.example.prachi.movieapp.requestToken;
import com.example.prachi.movieapp.sessionId;
import com.squareup.picasso.Callback;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

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

 @GET("movie/{movie_id}/reviews?api_key=1b54ff0e150f8aa8199d7fac9a3c5751")
 Call<ReviewResponse> getmoviereviews(@Path("movie_id") int movie_id);

 @GET("authentication/token/new?api_key=1b54ff0e150f8aa8199d7fac9a3c5751")
 Call<requestToken> getRequestoken();

 @GET("authentication/token/validate_with_login?api_key=1b54ff0e150f8aa8199d7fac9a3c5751")
 Call<RequestValidation> validaterequesttoken(@QueryMap Map<String,String> options);

@GET("authentication/session/new?api_key=1b54ff0e150f8aa8199d7fac9a3c5751")
 Call<sessionId> getsessionId(@Query("request_token") String request_token);

 @POST("movie/{movie_id}/rating?api_key=1b54ff0e150f8aa8199d7fac9a3c5751")
 Call<ratemovieresponse> postmovierating(@Path("movie_id") int movie_id, @Query("session_id") String session_id, @Body ratingrequestbody requestbody);






}

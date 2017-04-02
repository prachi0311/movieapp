package com.example.prachi.movieapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.prachi.movieapp.Network.ApiCLient;
import com.example.prachi.movieapp.Network.ApiInterface;
import com.example.prachi.movieapp.Network.PopularMovies;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class genreMovieActivity extends AppCompatActivity {
    MovieInfo movieobject=new MovieInfo();
    ArrayList<MovieInfo> movielist;
    movieAdapter adapter;
    int genreid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popular_movie_fragment);
        Intent i=getIntent();
        genreid=i.getIntExtra("genreid",-1);
        movielist=new ArrayList<>();
        RecyclerView recyclerView=(RecyclerView) findViewById(R.id.recycleview);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager= new GridLayoutManager(this,2);
        adapter=new movieAdapter(this,movielist);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        fetchPopularMovies();
    }
    private void fetchPopularMovies() {
        ApiInterface apiinterface= ApiCLient.getApiinterface();
        Call<PopularMovies> genremovies=apiinterface.getGenreMovies(genreid);
        genremovies.enqueue(new Callback<PopularMovies>() {
            @Override
            public void onResponse(Call<PopularMovies> call, Response<PopularMovies> response) {
                if(response.isSuccessful()){
                    movielist.clear();
                    PopularMovies body=response.body();
                    movielist.addAll(body.getMovieinfo());
                    Log.i("response",movielist.get(0).getGenres().get(0).toString());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<PopularMovies> call, Throwable t) {
                Log.i("response","failure");
                Log.d("response",t.toString());

            }
        });


    }
}

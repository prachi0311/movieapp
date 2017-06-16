package com.example.prachi.movieapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.prachi.movieapp.Network.ApiCLient;
import com.example.prachi.movieapp.Network.ApiInterface;
import com.example.prachi.movieapp.Network.PopularMovies;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by prachi on 2/4/17.
 */

public class UpcomingMovieFragment extends Fragment {
    MovieInfo movieobject=new MovieInfo();
    ArrayList<MovieInfo> movielist;
    movieAdapter adapter;
    String sessionid;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.popular_movie_fragment,container,false);
        movielist=new ArrayList<>();
        RecyclerView recyclerView=(RecyclerView) view.findViewById(R.id.recycleview);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager= new GridLayoutManager(getActivity(),2);
        sessionid=getActivity().getIntent().getStringExtra("sessionID");
        adapter=new movieAdapter(getActivity(),movielist,sessionid);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        fetchPopularMovies();
        return view;
    }
    private void fetchPopularMovies() {
        ApiInterface apiinterface= ApiCLient.getApiinterface();
        Call<PopularMovies> UpcomingMovies=apiinterface.getUpcomingMovies();
        UpcomingMovies.enqueue(new Callback<PopularMovies>() {
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

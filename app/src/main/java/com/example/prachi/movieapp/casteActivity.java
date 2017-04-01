package com.example.prachi.movieapp;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.example.prachi.movieapp.Network.ApiCLient;
import com.example.prachi.movieapp.Network.ApiInterface;
import com.example.prachi.movieapp.Network.MovieTrailer;
import com.example.prachi.movieapp.Network.moviecastresponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.v7.widget.RecyclerView.VERTICAL;

public class casteActivity extends AppCompatActivity {
    castAdapter adapter;
    int movieid;
    ArrayList<MovieCast> castList;
    ArrayList<castMovieListInfo> castmovielist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caste);
        Intent i=getIntent();
        movieid=i.getIntExtra("movieid",-1);
        castList=new ArrayList<>();
        RecyclerView recyclerView=(RecyclerView) findViewById(R.id.castrecycleview);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager= new GridLayoutManager(this,2);
        adapter=new castAdapter(castList,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        fetchcast(movieid);
    }

    public void fetchcast(int id) {
        Log.i("mvieid",String.valueOf(id));
        ApiInterface apiinterface= ApiCLient.getApiinterface();
        Call<moviecastresponse> call=apiinterface.getmoviecast(id);
        call.enqueue(new Callback<moviecastresponse>() {
            @Override
            public void onResponse(Call<moviecastresponse> call, Response<moviecastresponse> response) {
                if(response.isSuccessful()){
                    castList.clear();
                    moviecastresponse body=response.body();
                    castList.addAll(body.getCast());
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(Call<moviecastresponse> call, Throwable t) {
                Log.d("castresponse",t.toString());

            }
        });

    }
}

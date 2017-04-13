package com.example.prachi.movieapp;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.example.prachi.movieapp.Network.ApiCLient;
import com.example.prachi.movieapp.Network.ApiInterface;
import com.example.prachi.movieapp.Network.CastMovieInfo;
import com.example.prachi.movieapp.Network.CastmovieList;
import com.example.prachi.movieapp.Network.PopularMovies;
import com.example.prachi.movieapp.Network.moviecastresponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CastmembermovielistActivity extends AppCompatActivity {
    int personid;
    ArrayList<castMovieListInfo> castmembermovielist;
    CastMovieInfo castmovieitem;
    ArrayList<CastMovieInfo> castmovielist;
    ArrayList<Integer> movieids;
    castmovieadapter adapter;
    int movieid;
    int j,k=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_castmembermovielist);
        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        Log.i("castmembermovielist","here");
        Intent i=getIntent();
        personid=i.getIntExtra("personId",-1);
        actionBar.setTitle(i.getStringExtra("actorName")+"'s Movies");
        castmembermovielist=new ArrayList<>();
        castmovielist=new ArrayList<>();
        movieids=new ArrayList<>();
        RecyclerView recyclerView=(RecyclerView) findViewById(R.id.castmembermovielistrecycleview);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager= new GridLayoutManager(this,2);
        adapter=new castmovieadapter(this,castmovielist);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        fetchmovielist(personid);

    }

    private void fetchmovielist(int personid) {
        final ApiInterface apiinterface = ApiCLient.getApiinterface();
        Call<CastmovieList> call = apiinterface.getcastmovielist(personid);
        call.enqueue(new Callback<CastmovieList>() {
            @Override
            public void onResponse(Call<CastmovieList> call, Response<CastmovieList> response) {
                if (response.isSuccessful()) {
                    Log.i("casteresponse", String.valueOf(response.code()));
                    castmembermovielist.clear();
                    CastmovieList body = response.body();
                    castmembermovielist.addAll(body.getCast());
                    for (int i = 0; i < castmembermovielist.size(); i++) {
                        movieids.add(i, castmembermovielist.get(i).getMovieid());
                    }
                    Log.i("idsize",String.valueOf(movieids.size()));
                }
                for(j=0;j<movieids.size();j++){

                    movieid=movieids.get(j);
                    Call<CastMovieInfo> castcall=apiinterface.getmovieinfo(movieid);
                    castcall.enqueue(new Callback<CastMovieInfo>() {
                        @Override
                        public void onResponse(Call<CastMovieInfo> call, Response<CastMovieInfo> response) {
                            if(response.isSuccessful()) {
                                Log.i("castonresponse","successfull");
                                castmovielist.add(response.body());
                               // Log.i("bleh",String.valueOf(castmovielist.size()));
                                    adapter.notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onFailure(Call<CastMovieInfo> call, Throwable t) {
                            Log.i("castresponse","fail");

                        }
                    });
                }
                Log.i("castmovielist",String.valueOf(castmovielist.size()));
            }

            @Override
            public void onFailure(Call<CastmovieList> call, Throwable t) {
                Log.i("casteresponse", "onfailure");

            }
        });


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return true;
    }

}

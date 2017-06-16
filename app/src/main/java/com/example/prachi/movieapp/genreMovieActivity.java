package com.example.prachi.movieapp;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

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
    String sessionid;
    int genreid;
    String genrename;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popular_movie_fragment);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        Intent i=getIntent();
        genreid=i.getIntExtra("genreid",-1);
        genrename=i.getStringExtra("genrename");
        actionBar.setTitle(genrename);
        movielist=new ArrayList<>();
        RecyclerView recyclerView=(RecyclerView) findViewById(R.id.recycleview);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager= new GridLayoutManager(this,2);
        sessionid=i.getStringExtra("sessionID");
        adapter=new movieAdapter(this,movielist,sessionid);
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
    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        //  Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_startingactivity, menu);
        MenuItem searchItem = menu.findItem(R.id.search_icon);

        SearchView searchView = (SearchView) searchItem.getActionView();
//        searchView.setOnQueryTextListener(n);
//        SearchManager searchManager=(SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        if(searchManager!=null){
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
//        searchView.setIconifiedByDefault(false);
//        searchView.setSubmitButtonEnabled(true);}
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                //Toast.makeText(startingactivity.this,query,Toast.LENGTH_SHORT).show();
//                ApiInterface apiInterface= ApiCLient.getApiinterface();
//                Call call=apiInterface.getSearchedMovies(query);
//                call.enqueue(new Callback() {
//                    @Override
//                    public void onResponse(Call call, Response response) {
//                        if(response.isSuccessful()){
//                            movielist.clear();
//                            PopularMovies body=response.body();
//                            movielist.addAll(body.getMovieinfo());
//
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call call, Throwable t) {
//
//                    }
//                });
//
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//
//                return false;
//            }
//        });
//
//
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        if (item.getItemId() == R.id.search_icon) {
            Intent i = new Intent();
            i.setClass(genreMovieActivity.this, SearchActivity.class);
            startActivity(i);
        }
       else if(item.getItemId()==android.R.id.home){
            finish();
        }
        return true;
    }

}

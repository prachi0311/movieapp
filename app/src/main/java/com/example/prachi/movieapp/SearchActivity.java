package com.example.prachi.movieapp;

import android.app.SearchManager;
import android.content.Context;
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

import static com.example.prachi.movieapp.R.id.popularmovies;

public class SearchActivity extends AppCompatActivity {
    ArrayList<MovieInfo> movielist;
    movieAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        movielist=new ArrayList<>();
        RecyclerView recyclerView=(RecyclerView) findViewById(R.id.searchrecycleview);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager= new GridLayoutManager(this,2);
        adapter=new movieAdapter(this,movielist,null);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
       // fetchSearchedMovies();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //  Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.searchviewmenu, menu);
        MenuItem searchItem = menu.findItem(R.id.search_button);

        SearchView searchView = (SearchView) searchItem.getActionView();
       // searchView.setOnQueryTextListener(n);
        SearchManager searchManager=(SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        if(searchManager!=null){
          searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
//        searchView.setIconifiedByDefault(false);
        searchView.setSubmitButtonEnabled(true);
        searchView.setIconified(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Toast.makeText(startingactivity.this,query,Toast.LENGTH_SHORT).show();
                ApiInterface apiinterface= ApiCLient.getApiinterface();
                Call<PopularMovies> searchedmovies=apiinterface.getSearchedMovies(query);
                searchedmovies.enqueue(new Callback<PopularMovies>() {
                    @Override
                    public void onResponse(Call<PopularMovies> call, Response<PopularMovies> response) {
                        if(response.isSuccessful()){
                            movielist.clear();
                            PopularMovies body=response.body();
                            movielist.addAll(body.getMovieinfo());
                            Log.i("searchresponse",String.valueOf(movielist.size()));
                            for(int i=0;i<movielist.size();i++){
                                if(movielist.get(i).getPosterpath()==null || movielist.get(i).getBackdrop()==null ){
                                    movielist.remove(i);
                                }
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<PopularMovies> call, Throwable t) {
                        Log.i("response","failure");
                        Log.d("response",t.toString());

                    }
                });

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                movielist.clear();
                adapter.notifyDataSetChanged();
                return false;
            }
        });


        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return true;
    }

}

package com.example.prachi.movieapp;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prachi.movieapp.Network.ApiCLient;
import com.example.prachi.movieapp.Network.ApiInterface;
import com.example.prachi.movieapp.Network.PopularMovies;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class startingactivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    TabLayout tablayout;
    PopularMovieFragment popularMovieFragment;
    EditText moviesearch;
    Toolbar toolbar;
    String request_token;
    String password;
    String username;
    String sessionId;
    Map<String, String> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startingactivity);
        Intent i = getIntent();
        username = i.getStringExtra("username");
        password = i.getStringExtra("password");
        sessionId=i.getStringExtra("sessionID");
      //  gettoken();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tablayout = (TabLayout) findViewById(R.id.tablayout);
        moviesearch = (EditText) findViewById(R.id.moviesearch);
        data = new HashMap<>();

        popularMovieFragment = new PopularMovieFragment();
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        tablayout.setupWithViewPager(mViewPager);
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });

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
                i.setClass(startingactivity.this, SearchActivity.class);
                startActivity(i);
            }
            return true;
        }
        public class SectionsPagerAdapter extends FragmentPagerAdapter {

            public SectionsPagerAdapter(FragmentManager fm) {
                super(fm);
            }

            @Override
            public Fragment getItem(int position) {
                // getItem is called to instantiate the fragment for the given page.
                // Return a PlaceholderFragment (defined as a static inner class below).
                // return PlaceholderFragment.newInstance(position + 1);
                switch (position) {
                    case 2:
                        return new PopularMovieFragment();
                    case 3:
                        return new TopRatedMovieFragment();
                    case 1:
                        return new UpcomingMovieFragment();
                    case 0:
                        return new GenreFregment();

                }
                return null;
            }

            @Override
            public int getCount() {
                // Show 3 total pages.
                return 4;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 2:
                        return "POPULAR";
                    case 3:
                        return "TOP RATED";
                    case 1:
                        return "UPCOMING";
                    case 0:
                        return "GENRES";
                }
                return null;
            }
        }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}


package com.example.prachi.movieapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.prachi.movieapp.Network.ApiCLient;
import com.example.prachi.movieapp.Network.ApiInterface;
import com.example.prachi.movieapp.Network.CastMovieInfo;
import com.example.prachi.movieapp.Network.MovieTrailer;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviePageActivity extends AppCompatActivity {
    int movieid;
    ArrayList<movieTrailers> movieTrailerlist;
    String Trailerkey;
    ArrayList<String> TrailerName;
    int Duration;
    TextView movieDuration;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        Intent i=getIntent();
        movieid=i.getIntExtra("movieId",-1);
        movieTrailerlist=new ArrayList<>();
        TrailerName=new ArrayList<>();
       // Log.i("movieid",String.valueOf(movieid));
        ImageView backDropImage=(ImageView) findViewById(R.id.backdropimage);
        Picasso.with(this).load("http://image.tmdb.org/t/p/w1000/"+i.getStringExtra("movieBackdropPath")+"?api_key=1b54ff0e150f8aa8199d7fac9a3c5751").into(backDropImage);
        TextView movieTitle=(TextView) findViewById(R.id.moviepagetitle);
        TextView movieRating=(TextView) findViewById(R.id.moviepagerating);
        TextView movieReleaseyear=(TextView) findViewById(R.id.moivepagereleaseyear);
        TextView moviOverview=(TextView) findViewById(R.id.moviepageoverview);
        TextView moviegenre=(TextView) findViewById(R.id.moviegenre);
        TextView movieCast=(TextView) findViewById(R.id.moviecast);
        movieDuration=(TextView) findViewById(R.id.movieduration);
        Button movievideos=(Button) findViewById(R.id.moviepagevedios);
        Window window=getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browsingintent=new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://www.youtube.com/watch?v="+Trailerkey));
                startActivity(browsingintent);
            }
        });
        final String str=String.valueOf(i.getFloatExtra("movieRating",0));
        movieTitle.setText(i.getStringExtra("movieTitle"));
        movieRating.setText( "RATING   " + str);
        String releasedate =i.getStringExtra("movieReleaseDate");
        String releaseyear="";
        for (int j = 0; j < 4; j++) {
            releaseyear = releaseyear + releasedate.charAt(j);
        }
        Log.i("year",releaseyear);
        movieReleaseyear.setText(releaseyear+" ");
        moviegenre.setText(" "+i.getStringExtra("movieGenre"));
        moviOverview.setText(i.getStringExtra("movieOverview"));
        movievideos.setText("Watch more trailers");
        movieCast.setText("CAST");
        movievideos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alert=new AlertDialog.Builder(MoviePageActivity.this);
                View view= getLayoutInflater().inflate(R.layout.trailerdilog,null);
                alert.setView(view);
                alert.setCancelable(false);
                ListView trailerlist=(ListView) view.findViewById(R.id.trailerlist);
                ArrayAdapter<String> adapter=new ArrayAdapter<String>(MoviePageActivity.this,android.R.layout.simple_list_item_1,TrailerName);
                trailerlist.setAdapter(adapter);
                trailerlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent browsingintent=new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://www.youtube.com/watch?v="+movieTrailerlist.get(position).getKey()));
                        startActivity(browsingintent);

                    }
                });

                alert.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alert.setCancelable(true);
                    }
                });
                alert.create().show();
            }
        });
        movieCast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent();
                i.putExtra("movieid",movieid);
                i.setClass(MoviePageActivity.this,casteActivity.class);
                startActivity(i);

            }
        });

        fetchtrailers(movieid);
        fetchduration(movieid);
    }

    private void fetchduration(int movieid) {
        ApiInterface apiinterface= ApiCLient.getApiinterface();
        Call<CastMovieInfo> dcall=apiinterface.getmovieinfo(movieid);
        dcall.enqueue(new Callback<CastMovieInfo>() {
            @Override
            public void onResponse(Call<CastMovieInfo> call, Response<CastMovieInfo> response) {
                Duration=response.body().getRuntime();
                movieDuration.setText(" "+String.valueOf(Duration)+" min ");
            }

            @Override
            public void onFailure(Call<CastMovieInfo> call, Throwable t) {

            }
        });



    }


    private void fetchtrailers(int movieid) {

        ApiInterface apiinterface= ApiCLient.getApiinterface();
        Call<MovieTrailer> call=apiinterface.getmovieTrailer(movieid);
        call.enqueue(new Callback<MovieTrailer>() {
            @Override
            public void onResponse(Call<MovieTrailer> call, Response<MovieTrailer> response) {
                if(response.isSuccessful()){
                    movieTrailerlist.clear();
                    MovieTrailer body=response.body();
                    movieTrailerlist.addAll(body.getResults());
                    Trailerkey=movieTrailerlist.get(0).getKey();
                    for(int i=0;i<movieTrailerlist.size();i++) {
                        TrailerName.add(i,movieTrailerlist.get(i).getName());
                    }
                }
            }
            @Override
            public void onFailure(Call<MovieTrailer> call, Throwable t) {
                Log.d("trailerresponse",t.toString());

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("destroyed","due to cast activity");
    }
}

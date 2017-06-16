package com.example.prachi.movieapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prachi.movieapp.Network.ApiCLient;
import com.example.prachi.movieapp.Network.ApiInterface;
import com.example.prachi.movieapp.Network.CastMovieInfo;
import com.example.prachi.movieapp.Network.MovieTrailer;
import com.example.prachi.movieapp.Network.ReviewAdapter;
import com.example.prachi.movieapp.Network.ReviewResponse;
import com.example.prachi.movieapp.Network.moviecastresponse;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.R.id.list;
//import static com.example.prachi.movieapp.R.id.nestedscrollingview;
import static com.example.prachi.movieapp.R.id.reviewlist;
import static com.example.prachi.movieapp.R.id.scroll;
import static com.example.prachi.movieapp.R.id.scrollView;

public class MoviePageActivity extends AppCompatActivity {
    int movieid;
    ArrayList<movieTrailers> movieTrailerlist;
    String Trailerkey;
    ArrayList<String> TrailerName;
    int Duration;
    TrailerAdapter traileradapter;
    TextView movieDuration;
    castAdapter adapter;
    ArrayList<MovieCast> castList;
    Button rate;
   Spinner spinner;
    ReviewAdapter reviewadapter;
    ArrayList<reviewobjecttype> reviewList;
    ArrayList<String> reviews;
    ArrayList<String> authors;
    EditText rating;
    String sessionid;
    NestedScrollView scrollview;
    RecyclerView trailerrecyclerView;
    ImageView moviepagebg;
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
        sessionid=i.getStringExtra("sessionID");
        movieid=i.getIntExtra("movieId",-1);
        movieTrailerlist=new ArrayList<>();
        TrailerName=new ArrayList<>();
        reviews=new ArrayList<>();
        authors=new ArrayList<>();
        reviewList=new ArrayList<>();
       // Log.i("movieid",String.valueOf(movieid));
        ImageView backDropImage=(ImageView) findViewById(R.id.backdropimage);
        if(i.getStringExtra("movieBackdropPath")!=null)
        Picasso.with(this).load("http://image.tmdb.org/t/p/w1000/"+i.getStringExtra("movieBackdropPath")+"?api_key=1b54ff0e150f8aa8199d7fac9a3c5751").into(backDropImage);
        else
            Picasso.with(this).load("http://image.tmdb.org/t/p/w1000/"+i.getStringExtra("moviePosterPath")+"?api_key=1b54ff0e150f8aa8199d7fac9a3c5751").into(backDropImage);

        TextView movieTitle=(TextView) findViewById(R.id.moviepagetitle);
        TextView movieRating=(TextView) findViewById(R.id.moviepagerating);
        TextView movieReleaseyear=(TextView) findViewById(R.id.moivepagereleaseyear);
        TextView moviOverview=(TextView) findViewById(R.id.moviepageoverview);
        TextView moviegenre=(TextView) findViewById(R.id.moviegenre);
        TextView movieCast=(TextView) findViewById(R.id.moviecast);
        scrollview=(NestedScrollView) findViewById(R.id.nestedscrollingview);
       // moviepagebg=(ImageView) findViewById(R.id.moviepagebg);
       // trailerrecyclerView=(RecyclerView) findViewById(R.id.trailerrrecycleview);
        final TextView review=(TextView) findViewById(R.id.reviews);
        final TextView close=(TextView) findViewById(R.id.close);
        close.setPaintFlags(close.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        ImageView reviewdrop=(ImageView) findViewById(R.id.dropdownimage);
        ImageView rate=(ImageView) findViewById(R.id.rate);
        spinner=(Spinner) findViewById(R.id.spinner);
        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               final AlertDialog.Builder b= new  AlertDialog.Builder(MoviePageActivity.this);
                View view=getLayoutInflater().inflate(R.layout.rate_dialog,null);
                rating=(EditText) view.findViewById(R.id.dialograting);
                b.setView(view);
                b.setPositiveButton("SUBMIT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                      ApiInterface apiInterface=ApiCLient.getApiinterface();
                      Call<ratemovieresponse> call= apiInterface.postmovierating(movieid, sessionid, new ratingrequestbody(Integer.valueOf(rating.getText().toString())));
                       call.enqueue(new Callback<ratemovieresponse>() {
                           @Override
                           public void onResponse(Call<ratemovieresponse> call, Response<ratemovieresponse> response) {
                               Log.i("rateresponse","not successfull");
                               if(response.isSuccessful()){
                                   Toast.makeText(MoviePageActivity.this,"rated successfully",Toast.LENGTH_SHORT).show();
                               }
                           }

                           @Override
                           public void onFailure(Call<ratemovieresponse> call, Throwable t) {
                               Log.i("rateresponse","failure");

                           }
                       });
                        }
                });
                b.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        b.setCancelable(true);
                    }
                });

                b.create().show();
            }
        });
        movieDuration=(TextView) findViewById(R.id.movieduration);
       // Button movievideos=(Button) findViewById(R.id.moretrailers);
        Window window=getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Intent browsingintent=new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://www.youtube.com/watch?v="+Trailerkey));
////                startActivity(browsingintent);
//                Intent i=new Intent();
//                i.putExtra("youtubestring",Trailerkey);
//                i.setClass(MoviePageActivity.this,PlayerView.class);
//                startActivity(i);
//            }
//        });
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
        //movievideos.setText("Watch more trailers");
        movieCast.setText("CAST");
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alert=new AlertDialog.Builder(MoviePageActivity.this,R.style.MyAlertDialogTheme);
                View view= getLayoutInflater().inflate(R.layout.trailerdilog,null);
                alert.setView(view);
                alert.setCancelable(false);
                RecyclerView trailerrecyclerView=(RecyclerView) view.findViewById(R.id.trailerrrecycleview);
                trailerrecyclerView.setHasFixedSize(true);
                RecyclerView.LayoutManager TrailerlayoutManager= new LinearLayoutManager(MoviePageActivity.this, LinearLayout.VERTICAL,false);
                traileradapter=new TrailerAdapter(movieTrailerlist,MoviePageActivity.this);
                trailerrecyclerView.setAdapter(traileradapter);
                trailerrecyclerView.setLayoutManager(TrailerlayoutManager);
                traileradapter.notifyDataSetChanged();
                alert.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alert.setCancelable(true);
                    }
                });
                alert.create().show();
            }
        });
        castList=new ArrayList<>();
        final RecyclerView recyclerView=(RecyclerView) findViewById(R.id.castrecycleview);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(MoviePageActivity.this, LinearLayout.HORIZONTAL,false);
        adapter=new castAdapter(castList,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setFocusable(true);
        final RecyclerView reviewlist=(RecyclerView) findViewById(R.id.reviewlist);
        RecyclerView.LayoutManager reviewlayoutManager= new LinearLayoutManager(MoviePageActivity.this, LinearLayout.VERTICAL,false);
        reviewlist.setLayoutManager(reviewlayoutManager);
        reviewlist.setNestedScrollingEnabled(false);
        reviewadapter=new ReviewAdapter(this,reviewList);
        reviewlist.setAdapter(reviewadapter);

        final LinearLayout reviewLayout = (LinearLayout) findViewById(R.id.reviewLayout);
        reviewLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(reviewlist.getVisibility()==View.VISIBLE){

                    reviewlist.setVisibility(View.GONE);
                    close.setVisibility(View.GONE);}

                else {

                    scrollview.post(new Runnable() {
                        @Override
                        public void run() {
                            scrollview.fullScroll(ScrollView.FOCUS_DOWN);
                        }
                    });
                    close.setVisibility(View.VISIBLE);
                    reviewlist.setVisibility(View.VISIBLE);

                }
            }
        });
//        review.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(reviewlist.getVisibility()==View.VISIBLE){
//
//                reviewlist.setVisibility(View.GONE);
//                close.setVisibility(View.GONE);}
//
//                else {
//
//                    scrollview.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            scrollview.smoothScrollTo(0,scrollview.getBottom()-50);
//                        }
//                    });
//                    close.setVisibility(View.VISIBLE);
//                    reviewlist.setVisibility(View.VISIBLE);
//
//                }
//
//            }
//        });
//        reviewdrop.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(reviewlist.getVisibility()==View.VISIBLE){
//                    reviewlist.setVisibility(View.GONE);
//                    close.setVisibility(View.GONE);}
//                else {
//                    scrollview.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            scrollview.smoothScrollTo(0,scrollview.getBottom()-50);
//                        }
//                    });
//                    close.setVisibility(View.VISIBLE);
//                    reviewlist.setVisibility(View.VISIBLE);
//                }
//            }
//        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                close.setVisibility(View.GONE);
                reviewlist.setVisibility(View.GONE);
                scrollview.post(new Runnable() {
                    @Override
                    public void run() {
                        scrollview.fullScroll(ScrollView.FOCUS_UP);
                    }
                });

            }
        });
        fetchcast(movieid);
        fetchtrailers(movieid);
        fetchduration(movieid);
        fetchreviews(movieid);
    }

    private void fetchreviews(int movieid) {
        ApiInterface apiinterface= ApiCLient.getApiinterface();
        Call<ReviewResponse> dcall=apiinterface.getmoviereviews(movieid);
        dcall.enqueue(new Callback<ReviewResponse>() {
            @Override
            public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {
               ReviewResponse body=response.body();
                reviewList.addAll(body.getResults());
                for(int i=0;i<reviewList.size();i++){
                    reviews.add(reviewList.get(i).getContent());
                    authors.add(reviewList.get(i).getAuthor());
                }
                reviewadapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ReviewResponse> call, Throwable t) {

            }
        });


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
                    if(movieTrailerlist.size()!=0) {
                        Trailerkey = movieTrailerlist.get(0).getKey();
                        for (int i = 0; i < movieTrailerlist.size(); i++) {
                            TrailerName.add(i, movieTrailerlist.get(i).getName());
                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<MovieTrailer> call, Throwable t) {
                Log.d("trailerresponse",t.toString());

            }
        });
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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return true;
    }


}

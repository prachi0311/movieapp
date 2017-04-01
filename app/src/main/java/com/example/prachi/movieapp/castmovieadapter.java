package com.example.prachi.movieapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prachi.movieapp.Network.CastMovieInfo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by prachi on 29/3/17.
 */

public class castmovieadapter extends RecyclerView.Adapter<castmovieadapter.ViewHolder> {
    Context mcontext;
    ArrayList<CastMovieInfo> mcastmovielist;
    View view;
    String moviegenre;

    HashMap<Integer,String> genremapping;

    public castmovieadapter(Context context, ArrayList<CastMovieInfo> movielist){
        Log.i("castmovieadapter","here");
        mcontext=context;
        mcastmovielist=movielist;
        int[] id={28,12,16,35,80,99,18,10751,14,36,27,10402,9648,10749,878,10770,53,10752,37};
        String[] genre={"Action","Adventure","Animation","Comedy","Crime","Documentary","Drama","Family","Fantasy","History","Horror","Music","Mystery","Romance","Science Fiction","Tv Movie","Thriller","War","Western"};
        genremapping=new HashMap<>();
        for(int i=0;i<id.length;i++){
            if(!genremapping.containsKey(id[i]));
            genremapping.put(id[i],genre[i]);
        }

    }


    @Override
    public castmovieadapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.castmovielistitem, parent, false);

        castmovieadapter.ViewHolder vh=new castmovieadapter.ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(castmovieadapter.ViewHolder holder, final int position) {
        Log.i("castbindview","here");
        String releasedate = "";
        for (int i = 0; i < 4; i++) {
            releasedate = releasedate + mcastmovielist.get(position).getReleasedate().charAt(i);
        }
        holder.moviename.setText(mcastmovielist.get(position).getTitle());
        holder.releasedate.setText(releasedate);
        Picasso.with(mcontext).load("http://image.tmdb.org/t/p/w342/" + mcastmovielist.get(position).getPosterpath() + "?api_key=1b54ff0e150f8aa8199d7fac9a3c5751").into(holder.movieimage);
        View.OnClickListener mlistener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent();
                i.putExtra("movieId",mcastmovielist.get(position).getId());
                Log.i("info",String.valueOf(mcastmovielist.get(position).getId()));
                i.putExtra("movieTitle",mcastmovielist.get(position).getTitle());
                i.putExtra("movieReleaseDate",mcastmovielist.get(position).getReleasedate());
                i.putExtra("movieBackdropPath",mcastmovielist.get(position).getBackdrop());
                i.putExtra("movieOverview",mcastmovielist.get(position).getOverview());
                i.putExtra("movieRating",mcastmovielist.get(position).getRating());
                i.putExtra("movieLanguage",mcastmovielist.get(position).getLanguage());
                i.putExtra("movieId",mcastmovielist.get(position).getId());
                i.setClass(mcontext,MoviePageActivity.class);
                i.putExtra("movieGenre",mcastmovielist.get(position).getGenres().get(0).getName());
                mcontext.startActivity(i);
            }
        };
        holder.moviename.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mcontext,"clicked",Toast.LENGTH_SHORT).show();
                Log.i("clicked","clicked");
            }
        });
        holder.releasedate.setOnClickListener(mlistener);
        holder.movieimage.setOnClickListener(mlistener);

    }


    @Override
    public int getItemCount() {
        return mcastmovielist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView movieimage;
        TextView moviename;
        TextView releasedate;

        public ViewHolder(View itemView) {
            super(itemView);
            movieimage=(ImageView) itemView.findViewById(R.id.castmovieimage);
            moviename=(TextView) itemView.findViewById(R.id.castmoviename);
            releasedate=(TextView) itemView.findViewById(R.id.castmovieyear);

        }
    }
}



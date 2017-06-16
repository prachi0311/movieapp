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

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by prachi on 22/3/17.
 */

public class movieAdapter extends RecyclerView.Adapter<movieAdapter.ViewHolder>{
    Context mcontext;
    ArrayList<MovieInfo> mmovielist;
    View view;
    String moviegenre;
    String sessionid;

    HashMap<Integer,String> genremapping;

    public movieAdapter(Context context, ArrayList<MovieInfo> movielist,String sessionId){
        mcontext=context;
        mmovielist=movielist;
        sessionid=sessionId;
        int[] id={28,12,16,35,80,99,18,10751,14,36,27,10402,9648,10749,878,10770,53,10752,37};
        String[] genre={"Action","Adventure","Animation","Comedy","Crime","Documentary","Drama","Family","Fantasy","History","Horror","Music","Mystery","Romance","Science Fiction","Tv Movie","Thriller","War","Western"};
           genremapping=new HashMap<>();
        for(int i=0;i<id.length;i++){
            if(!genremapping.containsKey(id[i]));
              genremapping.put(id[i],genre[i]);
        }

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
         view=LayoutInflater.from(parent.getContext())
                .inflate(R.layout.moviegridelement, parent, false);

        ViewHolder vh=new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        String releasedate = "";
        if(mmovielist.get(position).getReleasedate().length()>1) {
            for (int i = 0; i < 4; i++) {
                releasedate = releasedate + mmovielist.get(position).getReleasedate().charAt(i);
            }
        }
        holder.moviename.setText(mmovielist.get(position).getTitle());
        holder.releasedate.setText(releasedate);
        if(mmovielist.get(position).getPosterpath()!=null)
        Picasso.with(mcontext).load("http://image.tmdb.org/t/p/w342/" + mmovielist.get(position).getPosterpath() + "?api_key=1b54ff0e150f8aa8199d7fac9a3c5751").into(holder.movieimage);
        View.OnClickListener listener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent();
                i.putExtra("movieId",mmovielist.get(position).getId());
                Log.i("info",String.valueOf(mmovielist.get(position).getId()));
                i.putExtra("movieTitle",mmovielist.get(position).getTitle());
                i.putExtra("movieReleaseDate",mmovielist.get(position).getReleasedate());
                i.putExtra("movieBackdropPath",mmovielist.get(position).getBackdrop());
                i.putExtra("movieOverview",mmovielist.get(position).getOverview());
                i.putExtra("moviePosterPath",mmovielist.get(position).getPosterpath());
                i.putExtra("movieRating",mmovielist.get(position).getRating());
                i.putExtra("movieLanguage",mmovielist.get(position).getLanguage());
                i.putExtra("movieId",mmovielist.get(position).getId());
                i.putExtra("sessionID",sessionid);
                i.setClass(mcontext,MoviePageActivity.class);
                Set<Integer> set = genremapping.keySet();
                if(mmovielist.get(position).getGenres().size()!=0) {
                    for (Integer j : set) {
                        if (mmovielist.get(position).getGenres().get(0) == j) {
                            moviegenre = genremapping.get(j);
                        }
                    }
                }
                i.putExtra("movieGenre",moviegenre);
                mcontext.startActivity(i);
            }
        };
        holder.moviename.setOnClickListener(listener);
        holder.releasedate.setOnClickListener(listener);
        holder.movieimage.setOnClickListener(listener);

    }


    @Override
    public int getItemCount() {
        return mmovielist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView movieimage;
        TextView moviename;
        TextView releasedate;

        public ViewHolder(View itemView) {
            super(itemView);
            movieimage=(ImageView) itemView.findViewById(R.id.movieimage);
            moviename=(TextView) itemView.findViewById(R.id.movieName);
            releasedate=(TextView) itemView.findViewById(R.id.movieReleaseDate);

        }
    }
}

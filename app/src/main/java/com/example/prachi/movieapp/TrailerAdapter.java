package com.example.prachi.movieapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by prachi on 14/4/17.
 */

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.ViewHolder> {
    ArrayList<movieTrailers> mtrailerList;
    Context mcontext;

    public TrailerAdapter(ArrayList<movieTrailers> mcastList, Context mcontext) {
        this.mtrailerList = mcastList;
        this.mcontext = mcontext;
    }
    @Override
    public TrailerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.traileritem,parent,false);
        TrailerAdapter.ViewHolder v=new TrailerAdapter.ViewHolder(view);
        return v;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Picasso.with(mcontext).load("http://img.youtube.com/vi/"+mtrailerList.get(position).getKey()+"/0.jpg").into(holder.trailerimage);
        holder.trailername.setText(mtrailerList.get(position).getName());
        View.OnClickListener listener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent();
                i.putExtra("youtubestring",mtrailerList.get(position).getKey());
                i.setClass(mcontext,PlayerView.class);
                mcontext.startActivity(i);
            }
        };

        holder.trailername.setOnClickListener(listener);
        holder.trailerimage.setOnClickListener(listener);
    }

    @Override
    public int getItemCount() {
        return mtrailerList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView trailerimage;
        TextView trailername;

        public ViewHolder(View itemView) {
            super(itemView);
            trailerimage=(ImageView) itemView.findViewById(R.id.tarilerthumbnail);
            trailername=(TextView) itemView.findViewById(R.id.trailerName);
        }
    }
}

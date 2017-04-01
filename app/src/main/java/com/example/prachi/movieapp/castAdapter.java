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

/**
 * Created by prachi on 28/3/17.
 */

public class castAdapter extends RecyclerView.Adapter<castAdapter.ViewHolder> {
   ArrayList<MovieCast> mcastList;
    Context mcontext;

    public castAdapter(ArrayList<MovieCast> mcastList, Context mcontext) {
        this.mcastList = mcastList;
        this.mcontext = mcontext;
    }

    @Override
    public castAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.castlistitem, parent, false);

        castAdapter.ViewHolder vh=new castAdapter.ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(castAdapter.ViewHolder holder, final int position) {
        Log.i("here","hereinbindview");
        holder.charactername.setText(mcastList.get(position).getName());
        holder.characterplayed.setText(mcastList.get(position).getCharacter());
        Picasso.with(mcontext).load("http://image.tmdb.org/t/p/w342/" + mcastList.get(position).getCastimage() + "?api_key=1b54ff0e150f8aa8199d7fac9a3c5751").into(holder.characterimage);
        View.OnClickListener listner=new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent();
                i.putExtra("personId",mcastList.get(position).getId());
                i.setClass(mcontext,CastmembermovielistActivity.class);
                mcontext.startActivity(i);

            }
        };
        holder.characterimage.setOnClickListener(listner);
        holder.characterplayed.setOnClickListener(listner);
        holder.charactername.setOnClickListener(listner);

    }

    @Override
    public int getItemCount() {
        return mcastList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView charactername;
        ImageView characterimage;
        TextView characterplayed;
        public ViewHolder(View itemView) {
            super(itemView);
            characterimage=(ImageView) itemView.findViewById(R.id.characterimage);
            charactername=(TextView) itemView.findViewById(R.id.castmembername);
            characterplayed=(TextView) itemView.findViewById(R.id.charactername);
        }
    }
}

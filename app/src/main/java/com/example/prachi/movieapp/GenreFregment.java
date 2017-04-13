package com.example.prachi.movieapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by prachi on 2/4/17.
 */

public class GenreFregment extends Fragment{
  ArrayList<genreitem> genres;
    int[] genreid={28,12,16,35,80,99,18,10751,14,36,27,10402,9648,10749,878,10770,53,10752,37};
    String[] genre={"Action","Adventure","Animation","Comedy","Crime","Documentary","Drama","Family","Fantasy","History","Horror","Music","Mystery","Romance","Science Fiction","Tv Movie","Thriller","War","Western"};
    ArrayAdapter<String> adapter;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.genrelistlayout, container, false);
        genres=new ArrayList<>();
        for(int i=0;i<19;i++){
            genreitem item=new genreitem();
            item.setGenreID(genreid[i]);
            item.setGenrename(genre[i]);
            genres.add(item);
        }
        ListView listView=(ListView) view.findViewById(R.id.genrelistview);
        adapter= new ArrayAdapter<String>(getActivity(),R.layout.genrelistitem,genre);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i=new Intent();
                i.putExtra("genreid",genreid[position]);
                i.setClass(getActivity(),genreMovieActivity.class);
                getActivity().startActivity(i);
            }
        });
        return view;
    }




}

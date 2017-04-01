package com.example.prachi.movieapp.Network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by prachi on 23/3/17.
 */

public class ApiCLient {

   static ApiInterface apiinterface;

   public static ApiInterface getApiinterface(){
       if(apiinterface==null){

           Retrofit retrofit= new Retrofit.Builder().baseUrl("http://api.themoviedb.org/3/")
                   .addConverterFactory(GsonConverterFactory.create()).build();
                apiinterface=retrofit.create(ApiInterface.class);
       }
        return apiinterface;
   }

}

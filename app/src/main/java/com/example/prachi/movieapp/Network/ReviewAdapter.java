package com.example.prachi.movieapp.Network;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.prachi.movieapp.R;
import com.example.prachi.movieapp.castAdapter;
import com.example.prachi.movieapp.reviewobjecttype;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by prachi on 27/4/17.
 */

   public class ReviewAdapter  extends RecyclerView.Adapter<ReviewAdapter.ViewHolder>{
    Context mcontext;
    ArrayList<reviewobjecttype> mlist;
    public ReviewAdapter(Context context, ArrayList<reviewobjecttype> list) {
        mcontext=context;
        mlist=list;
        Log.i("reviewlistsixe",String.valueOf(mlist.size()));
    }

    @Override


    public ReviewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.spinner_review_item, parent, false);

        ReviewAdapter.ViewHolder vh=new ReviewAdapter.ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.author.setText(mlist.get(position).getAuthor());
        holder.review.setText(mlist.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView review;
        TextView author;
        public ViewHolder(View itemView) {
            super(itemView);
            review=(TextView) itemView.findViewById(R.id.review);
            author=(TextView) itemView.findViewById(R.id.author);
        }
    }



}

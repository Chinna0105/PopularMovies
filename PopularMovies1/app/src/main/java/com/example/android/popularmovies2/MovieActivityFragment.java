package com.example.android.popularmovies2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/*
 */
public class MovieActivityFragment extends Fragment {

    public MovieActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView1 = inflater.inflate(R.layout.fragment_movie, container, false);
        //The below steps load the bigger image and the correspinding details about the movie
        ImageView img = (ImageView) rootView1.findViewById(R.id.movie1);
        Picasso.with(getContext())
                .load(Values.urlString1[Values.k])
                .into(img);
        TextView textView1 = (TextView) rootView1.findViewById(R.id.title);
        textView1.setText(Values.title[Values.k]);
        TextView textView2 = (TextView) rootView1.findViewById(R.id.overview);
        textView2.setText(Values.description[Values.k]);
        TextView textView3 = (TextView) rootView1.findViewById(R.id.rating);
        textView3.setText("("+Values.rating[Values.k]+"/10)");
        TextView textView4 = (TextView) rootView1.findViewById(R.id.releaseDate);
        textView4.setText("Release Date: "+Values.releaseDate[Values.k]);
        return rootView1;
    }
}

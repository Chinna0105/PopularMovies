package com.example.android.popularmovies2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.concurrent.TimeUnit;

//This class start the AsyncTask (FetchMoviesTask, and also inflates the main layout


public class MainActivityFragment extends Fragment {


    public MainActivityFragment() {
    }
    //Define gridView here, so that it can be accessed across methods
    GridView gridView = null;

    @Override
    public void onCreate(Bundle SavedInstanceState) {
        super.onCreate(SavedInstanceState);

        FetchMoviesTask moviesTask = new FetchMoviesTask();
        moviesTask.execute();
        /*Since AsyncTask runs on background thread, and it takes time to complete and
        retun the image URLs, we should wait for sometime before executing the main thread further.
        This is because in the subsequent steps, we will be using the URLs returned by AsyncTask, and these URLs
        will be available only after sometime. If we don't wait, nothing will be shown.
         */
        try {
            moviesTask.get(10000, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        gridView = (GridView) rootView.findViewById(R.id.gridView);
        gridView.setAdapter(new ImageAdapter(getContext()));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Values.k = position;
                //The below steps start a new activity to show the selected movie details
                Intent intent = new Intent(getActivity(), MovieActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }

}


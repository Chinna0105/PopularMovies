package com.example.android.popularmovies2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
/*This is the main activity and it displays the UI using it' fragment
and it also creates actions (sort by "Latest", "Popularity", "Rating" etc.
*/
public class MainActivity extends AppCompatActivity {
 public static Context context;

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
         context = getApplicationContext();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //if sort by "Latest" is selected
        if (id == R.id.action_latest) {
            MovieActivity detailFragment = new MovieActivity();
            Intent intent = new Intent(this, MainActivity.class);
            Values.query = "&sort_by=release_date.desc";
            startActivity(intent);
            return true;
        }
        //if sort by "Popular" is selected
        if (id == R.id.action_popular) {
            MovieActivity detailFragment = new MovieActivity();
            Intent intent = new Intent(this, MainActivity.class);
            Values.query = "&sort_by=popularity.desc";
            startActivity(intent);
            return true;
        }
        //if sort by "Rated" is selected
        if (id == R.id.action_highest_rated) {
            MovieActivity detailFragment = new MovieActivity();
            Intent intent = new Intent(this, MainActivity.class);
            Values.query = "&sort_by=vote_average.desc";
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

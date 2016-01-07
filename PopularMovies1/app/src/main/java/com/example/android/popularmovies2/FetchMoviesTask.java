package com.example.android.popularmovies2;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/*This task fetches the movie poster URL,rating, popularity, title, description, release date etc. using AsyncTask
 */
public class FetchMoviesTask extends AsyncTask<Void, Void, String[]> {
      //This method takes the JSON object and obtain the required details such as poster url, title etc.
      private String[] getMoviesFromJSON(String moviesString) throws JSONException {
        JSONObject moviesJson = new JSONObject(moviesString);
        JSONArray moviesArray = moviesJson.getJSONArray("results");
        String [] imagePath = new String[moviesArray.length()];
        for (int i = 0; i < moviesArray.length(); i++) {
            // Get the JSON object representing the individual movie
            JSONObject movieObject = moviesArray.getJSONObject(i);
            //get the image url
            imagePath[i] ="http://image.tmdb.org/t/p/w500/"+movieObject.getString("poster_path")+"&"+Values.api_key;
            //Store the corresponding details, so that they can be used later
            Values.title[i] =movieObject.getString("title");
            Values.description[i] =movieObject.getString("overview");
            Values.popularity[i] =movieObject.getString("popularity");
            Values.rating[i] =movieObject.getString("vote_average");
            Values.releaseDate[i] =movieObject.getString("release_date");
        }
        return imagePath;
    }

    protected String[] doInBackground(Void... params) {
        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw JSON response as a string.
        String moviesJsonStr = null;
        /*The app will show movies released in the past fortnigh. Accordingly start date is set to current date
        and end date is set to one fortnight ago.
         */
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date dateobj = new Date();
        Date dateobj1 = new Date();
        dateobj1.setDate(dateobj.getDate()-14);
        URL url;
        try {
            // The below URL will be used when the app starts first time
            if (Values.query ==null) {
                url = new URL("https://api.themoviedb.org/3/discover/movie?primary_release_date.gte=" + df.format(dateobj1) + "&primary_release_date.lte=" + df.format(dateobj) + "&api_key="+Values.api_key);
            }
            //The below URL will be used while sorting based on "Latest", "Popular","Highest Rated" etc.
            else {
                url = new URL("https://api.themoviedb.org/3/discover/movie?primary_release_date.gte=" + df.format(dateobj1) + "&primary_release_date.lte=" + df.format(dateobj) + "&api_key="+Values.api_key+Values.query);
            }

            // Create the request to TMDB, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                moviesJsonStr = null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                moviesJsonStr = null;
            }
            moviesJsonStr = buffer.toString();
        } catch (IOException e) {
            // If the code didn't successfully get the movie data, there's no point in attempting
            // to parse it.
            moviesJsonStr = null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("FetchMoviesTask", "Error closing stream", e);
                }
            }
        }
        try {
            return getMoviesFromJSON(moviesJsonStr);
        } catch (JSONException e) {
        }
        return null;
    }

    public void onPostExecute(String[] result) {

        Values.urlString1 = result;

    }

}


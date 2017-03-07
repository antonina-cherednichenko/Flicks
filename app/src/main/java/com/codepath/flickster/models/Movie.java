package com.codepath.flickster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by tonya on 3/7/17.
 */

public class Movie {

    private String posterPath;
    private String originalTitle;
    private String overview;

    public String getOverview() {
        return overview;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", posterPath);
    }

    public Movie(JSONObject jsonObject) throws JSONException {
        if (jsonObject.optString("poster_path") != null) {
            this.posterPath = jsonObject.getString("poster_path");
        }

        if (jsonObject.optString("original_title") != null) {
            this.originalTitle = jsonObject.getString("original_title");
        }

        if (jsonObject.optString("overview") != null) {
            this.overview = jsonObject.getString("overview");
        }

    }

    public static ArrayList<Movie> fromJSONArray(JSONArray array) {
        ArrayList<Movie> results = new ArrayList<>();

        for (int i = 0; i < array.length(); i++) {
            try {
                results.add(new Movie(array.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return results;
    }
}

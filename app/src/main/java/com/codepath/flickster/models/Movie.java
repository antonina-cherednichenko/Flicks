package com.codepath.flickster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by tonya on 3/7/17.
 */

public class Movie implements Serializable {

    private int id;
    private String posterPath;
    private String backdropPath;
    private String originalTitle;
    private String overview;
    private double voteAverage;
    private String releaseDate;


    public String getBackdropPath() {
        return String.format("https://image.tmdb.org/t/p/w1280/%s", backdropPath);
    }

    public String getOverview() {
        return overview;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w500/%s", posterPath);
    }

    public int getId() {
        return id;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public Movie(JSONObject jsonObject) throws JSONException {
        if (jsonObject.optString("poster_path") != null) {
            this.posterPath = jsonObject.getString("poster_path");
        }

        if (jsonObject.optString("backdrop_path") != null) {
            this.backdropPath = jsonObject.getString("backdrop_path");
        }

        if (jsonObject.optString("original_title") != null) {
            this.originalTitle = jsonObject.getString("original_title");
        }

        if (jsonObject.optString("overview") != null) {
            this.overview = jsonObject.getString("overview");
        }

        if (jsonObject.optString("vote_average") != null) {
            this.voteAverage = jsonObject.getDouble("vote_average");
        }

        if (jsonObject.optString("release_date") != null) {
            this.releaseDate = jsonObject.getString("release_date");
        }


        this.id = jsonObject.optInt("id");

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

    public static String videoSourceFromJSONArray(JSONArray array) {
        try {
            JSONObject jsonObject = array.getJSONObject(0);

            if (jsonObject.optString("key") != null) {
                return jsonObject.getString("key");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return "";
    }
}

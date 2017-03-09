package com.codepath.flickster;

import android.os.Bundle;
import android.widget.Toast;

import com.codepath.flickster.models.Movie;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by tonya on 3/8/17.
 */

public class PlayYoutubeActivity extends YouTubeBaseActivity {

    public static final String YT_API_KEY = "AIzaSyC2_EXzzOVaOsoQMPi7-WDD4HlcVah-hrE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_youtube);

        YouTubePlayerView youTubePlayerView =
                (YouTubePlayerView) findViewById(R.id.player);

        youTubePlayerView.initialize(YT_API_KEY,
                new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                        final YouTubePlayer youTubePlayer, boolean b) {


                        String url = String.format(" https://api.themoviedb.org/3/movie/%d/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed",
                                getIntent().getIntExtra("movieId", 0));

                        AsyncHttpClient client = new AsyncHttpClient();

                        client.get(url, new JsonHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                try {
                                    if (response.optJSONArray("results") != null) {
                                        JSONArray movieJsonResults = response.getJSONArray("results");

                                        // do any work here to cue video, play video, etc.
                                        youTubePlayer.cueVideo(Movie.videoSourceFromJSONArray(movieJsonResults));
                                        //youTubePlayer.setFullscreen(true);
                                        // or to play immediately
                                        // youTubePlayer.loadVideo("5xVh-7ywKpE");
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                                super.onFailure(statusCode, headers, responseString, throwable);
                            }
                        });


                    }

                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                        YouTubeInitializationResult youTubeInitializationResult) {
                        Toast.makeText(PlayYoutubeActivity.this, "Youtube Failed!", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}

package com.codepath.flickster;

import android.os.Bundle;
import android.widget.Toast;

import com.codepath.flickster.models.Movie;
import com.codepath.flickster.utils.OkHttpHelper;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

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

                        OkHttpHelper.httpCall(url, new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                e.printStackTrace();
                            }

                            @Override
                            public void onResponse(Call call, final Response response) throws IOException {
                                if (!response.isSuccessful()) {
                                    throw new IOException("Unexpected code " + response);
                                }

                                String responseData = response.body().string();
                                try {
                                    JSONObject json = new JSONObject(responseData);
                                    if (json.optJSONArray("results") != null) {
                                        final JSONArray movieJsonResults = json.getJSONArray("results");
                                        PlayYoutubeActivity.this.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                //Handle UI here
                                                youTubePlayer.setFullscreen(true);
                                                youTubePlayer.loadVideo(Movie.videoSourceFromJSONArray(movieJsonResults));
                                            }
                                        });
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
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

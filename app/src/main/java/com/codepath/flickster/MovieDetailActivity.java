package com.codepath.flickster;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.codepath.flickster.models.Movie;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class MovieDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        Movie movie = (Movie) getIntent().getExtras().getSerializable("movie");

        ImageView image = (ImageView) findViewById(R.id.movieImage);
        TextView name = (TextView) findViewById(R.id.movieName);
        RatingBar rating = (RatingBar) findViewById(R.id.movieRating);
        TextView description = (TextView) findViewById(R.id.movieDescription);

        //set description attributes
        Picasso.with(this).
                load(movie.getBackdropPath()).
                transform(new RoundedCornersTransformation(6, 6))
                .into(image);


        name.setText(movie.getOriginalTitle());
        rating.setRating((float) movie.getVoteAverage() / 2);
        description.setText(movie.getOverview());

    }
}

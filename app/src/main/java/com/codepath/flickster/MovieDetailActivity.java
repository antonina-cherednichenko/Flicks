package com.codepath.flickster;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.codepath.flickster.models.Movie;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class MovieDetailActivity extends AppCompatActivity {

    @BindView(R.id.movieImage) ImageView image;
    @BindView(R.id.movieName) TextView name;
    @BindView(R.id.movieRating) RatingBar rating;
    @BindView(R.id.movieDescription)TextView description;
    @BindView(R.id.releaseDate) TextView releaseDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);

        final Movie movie = (Movie) getIntent().getExtras().getSerializable("movie");

        //set description attributes
        Picasso.with(this).
                load(movie.getBackdropPath()).
                transform(new RoundedCornersTransformation(6, 6))
                .into(image);


        name.setText(movie.getOriginalTitle());
        rating.setRating((float) movie.getVoteAverage() / 2);
        description.setText(movie.getOverview());
        releaseDate.setText(String.format("Release Date: %s", movie.getReleaseDate()));

        image.setOnClickListener(new ImageView.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MovieDetailActivity.this, PlayYoutubeActivity.class);
                intent.putExtra("movieId", movie.getId());
                startActivity(intent);
            }
        });

    }
}

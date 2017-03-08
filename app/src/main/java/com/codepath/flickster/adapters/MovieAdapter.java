package com.codepath.flickster.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.flickster.PlayYoutubeActivity;
import com.codepath.flickster.R;
import com.codepath.flickster.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by tonya on 3/7/17.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private List<Movie> movies;
    private Context context;

    private final static int POPULAR_MOVIE = 1;
    private final static int NOT_SO_POPULAR_MOVIE = 0;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    @Override
    public int getItemViewType(int position) {
        Movie movie = movies.get(position);
        return movie.getVoteAverage() > 5 ? POPULAR_MOVIE : NOT_SO_POPULAR_MOVIE;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == NOT_SO_POPULAR_MOVIE) {
            View view = LayoutInflater.
                    from(parent.getContext()).
                    inflate(R.layout.not_so_popular_item_movie, parent, false);
            return new NotSoPopularMovieViewHolder(view);
        } else {
            View view = LayoutInflater.
                    from(parent.getContext()).
                    inflate(R.layout.popular_item_movie, parent, false);
            return new PopularMovieViewHolder(view);

        }
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        Movie movie = movies.get(position);

        //Measure parent width
        int displayWidth = context.getResources().getDisplayMetrics().widthPixels;

        if (holder.getItemViewType() == NOT_SO_POPULAR_MOVIE) {
            NotSoPopularMovieViewHolder movieHolder = (NotSoPopularMovieViewHolder) holder;

            movieHolder.title.setText(movie.getOriginalTitle());
            movieHolder.overview.setText(movie.getOverview());

            // get display orientation
            int orientation = context.getResources().getConfiguration().orientation;
            String imagePath = (orientation == Configuration.ORIENTATION_PORTRAIT) ? movie.getPosterPath() : movie.getBackdropPath();
            double imageSizeRatio = (orientation == Configuration.ORIENTATION_PORTRAIT) ? 1.8 : 1.6;

            Picasso.with(context).
                    load(imagePath).resize((int) (displayWidth / imageSizeRatio), 0).placeholder(R.drawable.placeholder)
                    .transform(new RoundedCornersTransformation(6, 6))
                    .into(movieHolder.image);
        } else if (holder.getItemViewType() == POPULAR_MOVIE) {
            PopularMovieViewHolder movieHolder = (PopularMovieViewHolder) holder;
            Picasso.with(context).
                    load(movie.getBackdropPath()).resize(displayWidth, 0).placeholder(R.drawable.placeholder)
                    .transform(new RoundedCornersTransformation(6, 6))
                    .into(movieHolder.image);
        }
    }


    public class MovieViewHolder extends RecyclerView.ViewHolder {

        public MovieViewHolder(View v) {
            super(v);
        }
    }

    public class NotSoPopularMovieViewHolder extends MovieViewHolder {
        public ImageView image;
        public TextView title;
        public TextView overview;

        public NotSoPopularMovieViewHolder(View v) {
            super(v);
            image = (ImageView) v.findViewById(R.id.ivMovieImage);
            title = (TextView) v.findViewById(R.id.tvTitle);
            overview = (TextView) v.findViewById(R.id.tvOverview);
        }
    }

    public class PopularMovieViewHolder extends MovieViewHolder {
        public ImageView image;

        public PopularMovieViewHolder(View v) {
            super(v);
            image = (ImageView) v.findViewById(R.id.ivMovieImage);

            v.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition(); // gets item position
                    if (position != RecyclerView.NO_POSITION) {
                        Intent intent = new Intent(context, PlayYoutubeActivity.class);
                        intent.putExtra("movieId", movies.get(position).getBackdropPath());
                        context.startActivity(intent);
                    }
                }
            });
        }
    }
}

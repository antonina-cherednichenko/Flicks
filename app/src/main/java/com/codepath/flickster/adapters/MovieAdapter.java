package com.codepath.flickster.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.item_movie, parent, false);

        final MovieViewHolder holder = new MovieViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        Movie movie = movies.get(position);
        holder.title.setText(movie.getOriginalTitle());
        holder.overview.setText(movie.getOverview());

        Picasso.with(context).
                load(movie.getPosterPath()).transform(new RoundedCornersTransformation(5, 5))
                .placeholder(R.drawable.placeholder).into(holder.postImage);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }


    public class MovieViewHolder extends RecyclerView.ViewHolder {
        public ImageView postImage;
        public TextView title;
        public TextView overview;

        public MovieViewHolder(View v) {
            super(v);
            postImage = (ImageView) v.findViewById(R.id.ivMovieImage);
            title = (TextView) v.findViewById(R.id.tvTitle);
            overview = (TextView) v.findViewById(R.id.tvOverview);
        }
    }
}

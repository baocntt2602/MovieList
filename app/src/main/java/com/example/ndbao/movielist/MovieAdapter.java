package com.example.ndbao.movielist;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ndbao.movielist.data.model.Result;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    private static final String BASE_IMG_URL = "https://image.tmdb.org/t/p/w500/";
    private List<Result> moviesList;
    private Context context;

    public MovieAdapter(List<Result> moviesList, Context context) {
        this.moviesList = moviesList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

        Result result = moviesList.get(i);
        viewHolder.txtName.setText(result.getTitle());
        viewHolder.txtRating.setText(result.getVoteAverage().toString());
        viewHolder.txtPopularity.setText(result.getPopularity().toString());
        viewHolder.txtReleaseDate.setText(result.getReleaseDate());
        String IMG_URL = BASE_IMG_URL + result.getBackdropPath();
        Glide.with(context)
                .load(IMG_URL)
                .into(viewHolder.imgMovie);
    }
    @Override
    public int getItemCount() {
        return moviesList == null ? 0 : moviesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgMovie;
        TextView txtName;
        TextView txtRating;
        TextView txtReleaseDate;
        TextView txtPopularity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgMovie = itemView.findViewById(R.id.img_movie);
            txtName = itemView.findViewById(R.id.txtName);
            txtRating = itemView.findViewById(R.id.txtRating);
            txtReleaseDate = itemView.findViewById(R.id.txtReleaseDate);
            txtPopularity = itemView.findViewById(R.id.txtPopularity);
        }
    }

    public void updateMovies(List<Result> movies) {
        moviesList.addAll(movies);
        notifyDataSetChanged();
    }

}

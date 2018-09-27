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
import com.example.ndbao.movielist.utils.CustomItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private static final String BASE_URL_IMG = "https://image.tmdb.org/t/p/w500/";
    private CustomItemClickListener listener;
    private List<Result> movieResuls;
    private Context context;
    private RecyclerView.ViewHolder viewHolder = null;
    private boolean isLoadingAdded = false;

    public MovieAdapter(Context context, CustomItemClickListener listener) {
        movieResuls = new ArrayList<>();
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        switch (viewType) {
            case ITEM:
                View v1 = inflater.inflate(R.layout.movie_item, viewGroup,false);
                viewHolder = new MovieVH(v1);
                final RecyclerView.ViewHolder finalViewHolder = viewHolder;
                v1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onItemClick(v, finalViewHolder.getLayoutPosition() );
                    }
                });
                break;
            case LOADING:
                View v2 = inflater.inflate(R.layout.item_progress, viewGroup, false);
                viewHolder = new LoadingVH(v2);
                break;
        }
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        Result result = movieResuls.get(position);
        switch (getItemViewType(position)) {
            case ITEM:
                MovieVH movieVH = (MovieVH) viewHolder;
                movieVH.txtName.setText(result.getTitle());
                movieVH.txtRating.setText(result.getVoteAverage().toString());
                movieVH.txtPopularity.setText(result.getPopularity().toString());
                movieVH.txtReleaseDate.setText(result.getReleaseDate());
                String IMG_URL = BASE_URL_IMG + result.getBackdropPath();
                Glide.with(context)
                        .load(IMG_URL)
                        .into(movieVH.imgMovie);
                break;
            case LOADING:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return movieResuls == null ? 0 : movieResuls.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == movieResuls.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }


    // Helpers

    public void add(Result r) {
        movieResuls.add(r);
        notifyItemInserted(movieResuls.size() - 1);
    }

    public void updateMovies(List<Result> movies) {
        movieResuls.addAll(movies);
        notifyDataSetChanged();
    }

    public void remove(Result r) {
        int position = movieResuls.indexOf(r);
        if (position > -1) {
            movieResuls.remove(position);
            notifyItemRemoved(position);
        }
    }

    public Result getMovieDetail(int positon) {
        return movieResuls.get(positon);
    }

    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int positon = movieResuls.size() - 1;
        Result result = getItem(positon);

        if (result != null) {
            movieResuls.remove(positon);
            notifyItemRemoved(positon);
        }
    }

    public Result getItem(int positon) {
        return movieResuls.get(positon);
    }



    public class MovieVH extends RecyclerView.ViewHolder {
        ImageView imgMovie;
        TextView txtName;
        TextView txtRating;
        TextView txtReleaseDate;
        TextView txtPopularity;

        public MovieVH(@NonNull View itemView) {
            super(itemView);
            imgMovie = itemView.findViewById(R.id.img_movie);
            txtName = itemView.findViewById(R.id.txtName);
            txtRating = itemView.findViewById(R.id.txtRating);
            txtReleaseDate = itemView.findViewById(R.id.txtReleaseDate);
            txtPopularity = itemView.findViewById(R.id.txtPopularity);
        }
    }

    protected class LoadingVH extends RecyclerView.ViewHolder {

        public LoadingVH(@NonNull View itemView) {
            super(itemView);
        }
    }



}

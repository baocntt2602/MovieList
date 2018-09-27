package com.example.ndbao.movielist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ndbao.movielist.data.model.Result;

public class MovieDetailActivity extends AppCompatActivity {
    ImageView imgMovieDetail;
    TextView txtMovieName;
    private static final String BASE_URL_IMG = "https://image.tmdb.org/t/p/w500/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        imgMovieDetail = findViewById(R.id.img_movie_detail);
        txtMovieName = findViewById(R.id.movie_name_detail);
        Intent intent = getIntent();
        Result movie = intent.getParcelableExtra("moviedetail");
        txtMovieName.setText(movie.getTitle());
        Glide.with(this)
                .load(BASE_URL_IMG+movie.getBackdropPath())
                .into(imgMovieDetail);

    }
}

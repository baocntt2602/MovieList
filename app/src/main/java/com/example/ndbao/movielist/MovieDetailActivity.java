package com.example.ndbao.movielist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class MovieDetailActivity extends AppCompatActivity {
    ImageView imgMovieDetail;
    TextView txtMovieName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        imgMovieDetail = findViewById(R.id.img_movie_detail);
        txtMovieName = findViewById(R.id.movie_name_detail);
    }
}

package com.example.ndbao.movielist.data.model.remote;

import com.example.ndbao.movielist.data.model.MovieList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SOService {
    @GET("3/movie/top_rated?api_key=f7d99ad4fc2e4fff54e36188dcc15467&language=en-US&page=1")
    Call<MovieList> getMovieList(@Query("page") int i);
}

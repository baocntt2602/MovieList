package com.example.ndbao.movielist;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.AbsListView;

import com.example.ndbao.movielist.data.model.MovieList;
import com.example.ndbao.movielist.data.model.Result;
import com.example.ndbao.movielist.data.model.remote.APIUtils;
import com.example.ndbao.movielist.data.model.remote.SOService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    private MovieAdapter movieAdapter;
    private SOService mService;
    private List<Result> movieList;
    private int first_page = 1 ;
    private int currentPage = first_page;
    private static final int TOTAL_PAGES = 3;
    private int currentItems;
    private int totalItems;
    private int scrollOutItems;
    Boolean isScrolling = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler_view);
        loadMovies(first_page);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true;
                }
            }
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItems = layoutManager.getChildCount();
                totalItems = layoutManager.getItemCount();
                scrollOutItems = layoutManager.findFirstVisibleItemPosition();
                if (currentPage < TOTAL_PAGES) {
                    if (isScrolling && (currentItems + scrollOutItems == totalItems)) {
                        isScrolling = false;
                        fetchData();
                    }
                }
            }
        });

    }

    private void fetchData() {
        currentPage += 1;
        new Handler().postDelayed(new Runnable() {
            List<Result> movieList1;
            @Override
            public void run() {
                mService = APIUtils.getSOService();
                mService.getMovieList(currentPage).enqueue(new Callback<MovieList>() {
                    @Override
                    public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                        movieList1 = response.body().getResults();
                        movieAdapter.updateMovies(movieList1);
                    }
                    @Override
                    public void onFailure(Call<MovieList> call, Throwable t) {
                        Log.d("fail", t.toString());
                    }
                });
            }
        }, 2000);
    }

    private void loadMovies(int i) {
        mService = APIUtils.getSOService();
        mService.getMovieList(i).enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                movieList = response.body().getResults();
                layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(layoutManager);
                movieAdapter = new MovieAdapter(movieList, getApplicationContext());
                recyclerView.setAdapter(movieAdapter);
            }
            @Override
            public void onFailure(Call<MovieList> call, Throwable t) {
                Log.d("fail", t.toString());
            }
        });
    }
}

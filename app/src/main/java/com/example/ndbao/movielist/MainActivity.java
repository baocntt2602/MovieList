package com.example.ndbao.movielist;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.ProgressBar;

import com.example.ndbao.movielist.data.model.MovieList;
import com.example.ndbao.movielist.data.model.Result;
import com.example.ndbao.movielist.data.model.remote.APIUtils;
import com.example.ndbao.movielist.data.model.remote.SOService;
import com.example.ndbao.movielist.utils.PaginationScrollListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    MovieAdapter adapter;
    LinearLayoutManager linearLayoutManager;

    RecyclerView rv;

    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;

    private int TOTAL_PAGES = 5;
    private int currentPage = PAGE_START;
    private SOService movieService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv = findViewById(R.id.recycler_view);
        adapter = new MovieAdapter(this);

        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(linearLayoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(adapter);

        rv.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadNextPage();
                    }
                }, 1000);
            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }
        });
        movieService = APIUtils.getSOService();
        loadFirstPage();
    }

    private void loadFirstPage() {
        movieService.getMovieList(PAGE_START).enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                List<Result> results = response.body().getResults();
                adapter.updateMovies(results);

                if (currentPage <= TOTAL_PAGES)
                    adapter.addLoadingFooter();
                else isLastPage = true;
            }

            @Override
            public void onFailure(Call<MovieList> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void loadNextPage() {
        movieService.getMovieList(currentPage).enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                adapter.removeLoadingFooter();
                isLoading = false;

                List<Result> results = response.body().getResults();
                adapter.updateMovies(results);
                if (currentPage != TOTAL_PAGES) adapter.addLoadingFooter();
                else isLastPage = true;
            }

            @Override
            public void onFailure(Call<MovieList> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}

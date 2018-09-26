package com.example.ndbao.movielist.data.model.remote;

public class APIUtils {
    public static final String BASE_URL = "https://api.themoviedb.org/3/";

    public static SOService getSOService() {
        return RetrofitClient.getClient(BASE_URL).create(SOService.class);
    }
}

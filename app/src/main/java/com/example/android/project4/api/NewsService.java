package com.example.android.project4.api;

import com.example.android.project4.models.Response;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by amandhapola on 10/07/17.
 */

public interface NewsService {
    @GET("search")
    Call<Response> getNews(@Query("q") String query,
                           @Query("count") int count,
                           @Query("offset") int offset,
                           @Query("mkt") String mkt,
                           @Query("safeSearch") String safeSearch);
}

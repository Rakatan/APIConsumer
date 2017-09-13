package com.rakatan.apiconsumer.web;

import com.google.gson.JsonObject;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface WebService {

    @GET("api")
    Observable<Response<JsonObject>> getUsers(@Query("page") int page,
                                              @Query("results") int resultsPerPage,
                                              @Query("seed") String seed);
}

package com.rakatan.apiconsumer.web;

import com.rakatan.apiconsumer.web.responses.ResGetUsers;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface WebService {

    @GET("api")
    Observable<Response<ResGetUsers>> getUsers(@Query("page") int page,
                                               @Query("results") int resultsPerPage,
                                               @Query("seed") String seed);
}

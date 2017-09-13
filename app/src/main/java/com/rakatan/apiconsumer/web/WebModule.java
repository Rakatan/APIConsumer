package com.rakatan.apiconsumer.web;

import android.support.v4.util.Pair;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.rakatan.apiconsumer.models.User;
import com.rakatan.apiconsumer.web.requests.ReqGetUsers;
import com.rakatan.apiconsumer.web.responses.ResGetUsers;

import java.util.ArrayList;

import retrofit2.Response;
import rx.Observable;

import static com.rakatan.apiconsumer.backend.rx.ComposeUtils.ioMainScheduler;

public class WebModule implements IWebModule {
    private static final WebModule ourInstance = new WebModule();

    // Change web service class here if you need to change the retrofit itnerface.
    private WebService webService;

    public static WebModule getInstance() {
        return ourInstance;
    }

    private WebModule() {
        webService = ServiceBuilder.getService(WebService.class);
    }

    @Override
    public Observable<APIEnvelope<Pair<ArrayList<User>, Integer>>> getUsers(ReqGetUsers request) {
        return webService.getUsers(request.getPage(), request.getResultsPerPage(), request.getSeed())
                .compose(ioMainScheduler())
                .map(this::validateUserRequest);
    }

    private APIEnvelope<Pair<ArrayList<User>, Integer>> validateUserRequest(Response<JsonObject> getUsersResponse) {
        APIEnvelope<Pair<ArrayList<User>, Integer>> responseEnvelope = new APIEnvelope<>();

        if (!getUsersResponse.isSuccess()) { // Api Call failed somewhere along the line, notify the user
            responseEnvelope.setError(new ApiError(getUsersResponse.code(), getUsersResponse.message()));
            return responseEnvelope;
        } else {
            JsonObject rawResponse = getUsersResponse.body();
            if (rawResponse.isJsonPrimitive()) { // It seems that this API returns a simple string json if some problems occur
                responseEnvelope.setError(new ApiError(getUsersResponse.code(), rawResponse.getAsString()));
                return responseEnvelope;
            } else { // Generate the response
                ResGetUsers resGetUsers = new Gson().fromJson(rawResponse, ResGetUsers.class);
                responseEnvelope.setResponse(new Pair<>(resGetUsers.getResults(), resGetUsers.getInfo().getPage()));
                return responseEnvelope;
            }
        }
    }

}

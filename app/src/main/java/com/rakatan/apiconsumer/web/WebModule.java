package com.rakatan.apiconsumer.web;

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

    public Observable<APIEnvelope<ArrayList<User>>> getUsers(ReqGetUsers request) {
        return webService.getUsers(request.getPage(), request.getResultsPerPage(), request.getSeed())
                .compose(ioMainScheduler())
                .map(this::validateUserRequest);
    }

    private APIEnvelope<ArrayList<User>> validateUserRequest(Response<ResGetUsers> resGetUsersResponse) {
        //TODO Just do this
        return null;
    }

}

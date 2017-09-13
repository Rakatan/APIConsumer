package com.rakatan.apiconsumer.web;

import android.support.v4.util.Pair;

import com.rakatan.apiconsumer.models.User;
import com.rakatan.apiconsumer.web.requests.ReqGetUsers;

import java.util.ArrayList;

import rx.Observable;

public interface IWebModule {
    Observable<APIEnvelope<Pair<ArrayList<User>, Integer>>> getUsers(ReqGetUsers request);
}

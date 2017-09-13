package com.rakatan.apiconsumer.viewmodels.outputs;

import com.rakatan.apiconsumer.models.User;

import java.util.ArrayList;

import rx.Observable;

public interface UsersViewModelOutputs {
    Observable<Boolean> bigLoadingToggled();

    Observable<Boolean> smallLoadingToggled();

    Observable<ArrayList<User>> initialUsersReceived();

    Observable<ArrayList<User>> moreUsersReceived();

    Observable<User> selectedUser();
}

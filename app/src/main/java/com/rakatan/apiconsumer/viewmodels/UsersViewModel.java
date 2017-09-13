package com.rakatan.apiconsumer.viewmodels;

import android.arch.lifecycle.ViewModel;
import android.support.v4.util.Pair;

import com.rakatan.apiconsumer.Environment;
import com.rakatan.apiconsumer.models.User;
import com.rakatan.apiconsumer.viewmodels.errors.UsersViewModelErrors;
import com.rakatan.apiconsumer.viewmodels.inputs.UsersViewModelInputs;
import com.rakatan.apiconsumer.viewmodels.outputs.UsersViewModelOutputs;
import com.rakatan.apiconsumer.web.APIEnvelope;
import com.rakatan.apiconsumer.web.ApiError;
import com.rakatan.apiconsumer.web.IWebModule;
import com.rakatan.apiconsumer.web.requests.ReqGetUsers;

import java.util.ArrayList;

import rx.Observable;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;

public class UsersViewModel extends ViewModel
        implements UsersViewModelInputs, UsersViewModelOutputs, UsersViewModelErrors {
    IWebModule webModule;
    Environment environment;

    public final UsersViewModelInputs inputs = this;
    public final UsersViewModelOutputs outputs = this;
    public final UsersViewModelErrors errors = this;

    private int currentPage = 1;

    public void init(Environment environment) {
        this.environment = environment;
        webModule = environment.webModule();
    }

    //INPUTS

    @Override
    public void userClicked(User user) {
        userProvider.onNext(user);
    }

    @Override
    public void refreshUsers() {
        currentPage = 1;
        bigLoadingToggler.onNext(true);
        webModule.getUsers(new ReqGetUsers(currentPage, ReqGetUsers.USERS_PER_PAGE))
                .subscribe(this::manageResult);
    }

    @Override
    public void getMoreUsers() {
        smallLoadingToggler.onNext(true);
        webModule.getUsers(new ReqGetUsers(currentPage, ReqGetUsers.USERS_PER_PAGE))
                .subscribe(this::manageResult);
    }

    //OUTPUTS
    private BehaviorSubject<Boolean> bigLoadingToggler = BehaviorSubject.create();
    private BehaviorSubject<Boolean> smallLoadingToggler = BehaviorSubject.create();
    private BehaviorSubject<ArrayList<User>> initialUsersProvider = BehaviorSubject.create();
    private BehaviorSubject<ArrayList<User>> moreUsersProvider = BehaviorSubject.create();
    private BehaviorSubject<User> userProvider = BehaviorSubject.create();

    @Override
    public Observable<Boolean> bigLoadingToggled() {
        return bigLoadingToggler;
    }

    @Override
    public Observable<Boolean> smallLoadingToggled() {
        return smallLoadingToggler;
    }

    @Override
    public Observable<ArrayList<User>> initialUsersReceived() {
        return initialUsersProvider;
    }

    @Override
    public Observable<ArrayList<User>> moreUsersReceived() {
        return moreUsersProvider;
    }

    @Override
    public Observable<User> selectedUser() {
        return userProvider;
    }

    //ERRORS
    private PublishSubject<String> userErrorProvider = PublishSubject.create();

    @Override
    public Observable<String> getUsersErred() {
        return userErrorProvider;
    }

    //PROCESSING
    private void manageResult(APIEnvelope<Pair<ArrayList<User>, Integer>> usersAndPage) {
        if (usersAndPage.getError() != null) {
            ApiError error = usersAndPage.getError();

            userErrorProvider.onNext(error.getErrorMessage() + " "
                    + Integer.toString(error.getResultCode()));

            bigLoadingToggler.onNext(false);
            smallLoadingToggler.onNext(false);
        } else {
            int page = usersAndPage.getResponse().second;
            if (page == 1) {
                initialUsersProvider.onNext(usersAndPage.getResponse().first);
                bigLoadingToggler.onNext(false);
            } else {
                moreUsersProvider.onNext(usersAndPage.getResponse().first);
                smallLoadingToggler.onNext(false);
            }

            currentPage = page + 1;
        }
    }
}

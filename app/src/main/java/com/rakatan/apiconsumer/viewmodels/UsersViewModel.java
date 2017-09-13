package com.rakatan.apiconsumer.viewmodels;

import android.arch.lifecycle.ViewModel;

import com.rakatan.apiconsumer.Environment;
import com.rakatan.apiconsumer.models.User;
import com.rakatan.apiconsumer.viewmodels.errors.UsersViewModelErrors;
import com.rakatan.apiconsumer.viewmodels.inputs.UsersViewModelInputs;
import com.rakatan.apiconsumer.viewmodels.outputs.UsersViewModelOutputs;
import com.rakatan.apiconsumer.web.IWebModule;

public class UsersViewModel extends ViewModel
        implements UsersViewModelInputs, UsersViewModelOutputs, UsersViewModelErrors {
    IWebModule webModule;
    Environment environment;

    public final UsersViewModelInputs inputs = this;
    public final UsersViewModelOutputs outputs = this;
    public final UsersViewModelErrors errors = this;

    public void init(Environment environment) {
        this.environment = environment;
        webModule = environment.webModule();
    }

    //INPUTS

    @Override
    public void userClicked(User user) {

    }

    @Override
    public void getMoreUsers() {

    }

    //OUTPUTS

    //ERRORS
}

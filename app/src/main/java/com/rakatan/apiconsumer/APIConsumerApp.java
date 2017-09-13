package com.rakatan.apiconsumer;

import android.app.Application;

import com.rakatan.apiconsumer.web.WebModule;

public class APIConsumerApp extends Application{
    private Environment environment;

    @Override
    public void onCreate() {
        super.onCreate();

        prepareEnvironment();
    }

    private void prepareEnvironment() {
        environment = Environment.getInstance();
        environment.initializeWebModule(WebModule.getInstance());
    }

    public Environment environment() {
        return environment;
    }

}

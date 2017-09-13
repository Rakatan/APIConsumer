package com.rakatan.apiconsumer;

import com.github.pwittchen.reactivenetwork.library.ReactiveNetwork;
import com.rakatan.apiconsumer.backend.rx.ComposeUtils;
import com.rakatan.apiconsumer.web.IWebModule;

import rx.Observable;

public class Environment {
    private static Environment ourInstance;

    private IWebModule webModule;
    private Observable<Boolean> internetMonitor;

    public static Environment getInstance() {
        if (ourInstance == null) {
            ourInstance = new Environment();
        }
        return ourInstance;
    }

    private Environment() {
        internetMonitor = ReactiveNetwork.observeInternetConnectivity()
                .compose(ComposeUtils.ioMainScheduler());
    }

    public void initializeWebModule(IWebModule webModule) {
        this.webModule = webModule;
    }

    public IWebModule webModule() {
        return webModule;
    }

    public Observable<Boolean> internetMonitor() {
        return internetMonitor;
    }
}

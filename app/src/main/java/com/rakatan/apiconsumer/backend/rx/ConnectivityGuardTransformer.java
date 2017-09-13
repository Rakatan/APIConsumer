package com.rakatan.apiconsumer.backend.rx;

import android.support.v4.util.Pair;

import com.rakatan.apiconsumer.Environment;

import rx.Observable;

public class ConnectivityGuardTransformer<T> implements Observable.Transformer<T, T> {

    private Environment environment;

    public ConnectivityGuardTransformer(Environment environment) {
        this.environment = environment;
    }

    @Override
    public Observable<T> call(Observable<T> source) {
        return source.withLatestFrom(environment.internetMonitor(), Pair::new)
                .filter(pair -> pair.second)
                .map(pair -> pair.first);
    }
}

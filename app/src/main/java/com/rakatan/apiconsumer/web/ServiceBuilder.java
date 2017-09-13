package com.rakatan.apiconsumer.web;


import com.rakatan.apiconsumer.BuildConfig;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceBuilder {
    private static final String BASE_URL = "https://randomuser.me/";

    public static <S> S getService(Class<S> serviceClass) {
        Retrofit.Builder retrofitBuilder = getRetrofitBuilder();
        OkHttpClient okHttpClient = getOkHttpClient();

        Retrofit retrofit = retrofitBuilder
                .client(okHttpClient)
                .build();

        return retrofit.create(serviceClass);
    }

    private static Retrofit.Builder getRetrofitBuilder() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create());
    }

    private static OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.interceptors().clear();

        if (BuildConfig.DEBUG) {
            final HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.addInterceptor(interceptor);
        }

        return httpClient.build();
    }
}

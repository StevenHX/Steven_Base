package com.hx.steven.Mvvm;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public  abstract class BaseRepertory<T> {
    private Retrofit mRetrofit;
    private T mApiService;

    public abstract String getBaseUrl();
    public abstract Class<T> getServiceClass();
    public BaseRepertory() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public Retrofit getRetrofit(){
        return mRetrofit;
    }

    public T getApiService() {
        if (mApiService == null) {
            mApiService = mRetrofit.create(getServiceClass());
            return mApiService;
        }
        return mApiService;
    }
}

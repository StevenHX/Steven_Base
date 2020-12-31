package com.hx.stevenbase.app;

import com.hx.steven.app.AppInitBuilder;
import com.hx.steven.app.BaseApplication;
import com.hx.steven.manager.RetrofitNetManager;
import com.hx.stevenbase.http.ApiService;

/**
 * Created by user on 2018/1/15.
 */

public class App extends BaseApplication {

    @Override
    public AppInitBuilder getAppInitBuilder() {
        return new AppInitBuilder.Builder()
                .setInitLogger(true)
                .build();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            RetrofitNetManager.getInstance().init("https://www.wanandroid.com");
            RetrofitNetManager.getInstance().setApiService(RetrofitNetManager.getInstance().getRetrofit().create(ApiService.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

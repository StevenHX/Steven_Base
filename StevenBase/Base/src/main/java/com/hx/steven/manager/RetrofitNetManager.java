package com.hx.steven.manager;

import com.hx.steven.http.BaseApiService;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * retrofit 网络请求管理类
 */
public class RetrofitNetManager {
    private static final String TAG = "RetrofitNetManager";
    private static RetrofitNetManager instance;
    private static volatile BaseApiService apiService = null;
    private static Retrofit retrofit;

    public static RetrofitNetManager getInstance() {
        if (instance == null) {
            synchronized (RetrofitNetManager.class) {
                if (instance == null) {
                    instance = new RetrofitNetManager();
                }
            }
        }
        return instance;
    }

    public void init(String baseUrl) {
        Interceptor logInterceptor = getLogInterceptor();
        // 初始化okhttp
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logInterceptor)
                .build();

        // 初始化Retrofit
        retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public void init(String baseUrl, Interceptor... interceptors) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        // 添加自定义的拦截器
        for (Interceptor interceptor : interceptors) {
            builder.addInterceptor(interceptor);
        }
        // 添加默认日志拦截器
        builder.addInterceptor(getLogInterceptor());
        // 初始化okhttp
        OkHttpClient client = builder.build();

        // 初始化Retrofit
        retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private Interceptor getLogInterceptor() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        return logging;
    }

    public BaseApiService getApiService() {
        if (apiService == null) {
            synchronized (Object.class) {
                apiService = retrofit.create(BaseApiService.class);
            }
        }
        return apiService;
    }

}

package com.hx.stevenbase.http;

import android.util.Log;

import com.hx.steven.util.NetworkUtil;
import com.hx.stevenbase.BuildConfig;
import com.hx.stevenbase.app.App;

import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api {
    static final String BASE_URL = "https://www.wanandroid.com";
    private static final String TAG = "Api";

    private static Api mApiRetrofit;
    static private ApiService mApiService;
    /**
     * 拦截器添加通用请求头
     */
    Interceptor headInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            return chain.proceed(
                    chain.request()
                            .newBuilder()
                            .addHeader("Content-Type", "application/json")
                            .addHeader("os", "1")
                            .build());
        }
    };
    /**
     * 网络缓存拦截器
     */
    Interceptor netCachInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            boolean connected = NetworkUtil.isNetworkConnected(App.getAppContext());
            if (connected) {
                //如果有网络，缓存90s
                Response response = chain.proceed(request);
                int maxTime = 90;
                return response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, max-age=" + maxTime)
                        .build();
            }
            //如果没有网络，不做处理，直接返回
            return chain.proceed(request);

        }
    };
    /**
     * 应用缓存拦截器
     */
    Interceptor noNetCachInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            boolean connected = NetworkUtil.isNetworkConnected(App.getAppContext());
            //如果没有网络，则启用 FORCE_CACHE
            if (!connected) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();

                Response response = chain.proceed(request);
                String cacheControl = request.cacheControl().toString();
                return response.newBuilder()
                        .header("Cache-Control", cacheControl)
                        .removeHeader("Pragma")
                        .build();
            }
            //有网络的时候，这个拦截器不做处理，直接返回
            return chain.proceed(request);
        }
    };
    /**
     * 日志拦截器
     */
    Interceptor logIntercepter = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            long t1 = System.nanoTime();
            Log.d(TAG, String.format("Sending request %s on %s%n%s", request.url(), chain.connection(), request.headers()));
            Response response = chain.proceed(request);
            long t2 = System.nanoTime();
            Log.d(TAG, String.format("Received response for %s in %.1fms%n%s", response.request().url(), (t2 - t1) / 1e6d, response.headers()));
            return response;
        }
    };
    private Retrofit retrofit;
    //构造方法私有
    private Api() {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();


        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);//这里可以选择拦截级别
            builder.addInterceptor(loggingInterceptor);
        }

        File cacheFile = new File(App.getAppContext().getCacheDir(), "cache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100); //100Mb

        OkHttpClient okHttpClient = builder
                .readTimeout(1000, TimeUnit.MILLISECONDS)
                .connectTimeout(10000, TimeUnit.MILLISECONDS)
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                })
                .addInterceptor(headInterceptor)
                .addNetworkInterceptor(netCachInterceptor)
                .addInterceptor(noNetCachInterceptor)
                .cache(cache)
                .build();


        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        mApiService = retrofit.create(ApiService.class);
    }

    public static Api getInstance() {
        if (mApiRetrofit == null) {
            synchronized (Object.class) {
                if (mApiRetrofit == null) {
                    mApiRetrofit = new Api();
                }
            }
        }
        return mApiRetrofit;
    }

    public ApiService getApiService() {
        return mApiService;
    }
}
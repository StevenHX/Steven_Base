package com.hx.stevenbase.ui.bingGallery;

import com.hx.steven.http.BaseBean;
import com.hx.stevenbase.ui.Set.home.homeBannerBean;
import com.hx.stevenbase.ui.Set.home.homeBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class ImageRepertory {
    private Retrofit mRetrofit;
    private Service mApiService;

    public ImageRepertory() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl("https://cn.bing.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }
    public Service getApiService() {
        if (mApiService == null) {
            return mRetrofit.create(Service.class);
        }else {
            return mApiService;
        }
    }

    public interface Service {

        @GET("HPImageArchive.aspx")
        Observable<ImageBean> getImage(
                @Query("format") String format,
                @Query("idx") int idx,
                @Query("n") int n
        );

        @GET("https://wanandroid.com/article/listproject/{page}/json")
        Observable<BaseBean<homeBean>> getHomeArticles(@Path("page") int page);

        @GET("https://www.wanandroid.com/banner/json")
        Observable<BaseBean<List<homeBannerBean>>> getHomeBanners();

    }
}

package com.hx.stevenbase.ui.bingGallery;

import com.hx.steven.Mvvm.BaseRepertory;
import com.hx.steven.http.BaseBean;
import com.hx.stevenbase.ui.Set.home.homeBannerBean;
import com.hx.stevenbase.ui.Set.home.homeBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class ImageRepertory extends BaseRepertory<ImageRepertory.Service> {
    @Override
    public String getBaseUrl() {
        return "https://cn.bing.com/";
    }

    @Override
    public Class<Service> getServiceClass() {
        return Service.class;
    }

    public interface Service {

        @GET("HPImageArchive.aspx")
        Observable<ImageBean> getImage(
                @Query("format") String format,
                @Query("idx") int idx,
                @Query("n") int n
        );

        @GET("https://wanandroid.com/article/listproject/{page}/json")
        Observable<BaseBean<WanBean>> getHomeArticles(@Path("page") int page);

        @GET("https://www.wanandroid.com/banner/json")
        Observable<BaseBean<List<homeBannerBean>>> getHomeBanners();

    }
}

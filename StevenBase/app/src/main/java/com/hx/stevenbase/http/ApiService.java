package com.hx.stevenbase.http;

import com.hx.steven.http.BaseBean;
import com.hx.stevenbase.ui.Login.LoginBean;
import com.hx.stevenbase.ui.Login.LoginDto;
import com.hx.stevenbase.ui.Set.about.aboutBean;
import com.hx.stevenbase.ui.Set.about.aboutDto;
import com.hx.stevenbase.ui.Set.home.homeBannerBean;
import com.hx.stevenbase.ui.Set.home.homeBean;
import com.hx.stevenbase.ui.bingGallery.ImageBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    /**
     * 登录
     * @param loginDto
     * @return
     */
    @POST("/api-customer/center/login")
    Call<BaseBean<LoginBean>> login(@Body LoginDto loginDto);
    /**
     * about
     * @return
     */
    @POST("/api-customer/center/login")
    Observable<BaseBean<aboutBean>> about(@Body aboutDto about);


    /**
     * 首页数据
     * http://www.wanandroid.com/article/list/0/json
     *
     * @param page page
     */
    @GET("/article/list/{page}/json")
    Call<BaseBean<homeBean>> getHomeArticles(@Path("page") int page);
    /**
     * 获取首页banner
     */
    @GET("/banner/json")
    Call<BaseBean<List<homeBannerBean>>> getHomeBanners();

    @GET("https://cn.bing.com/HPImageArchive.aspx")
    Observable<ImageBean> getImage(
            @Query("format") String format,
            @Query("idx") int idx,
            @Query("n") int n
    );
}

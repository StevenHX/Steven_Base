package com.hx.stevenbase.http;

import com.hx.stevenbase.ui.Login.LoginBean;
import com.hx.stevenbase.ui.Login.LoginDto;
import com.hx.stevenbase.ui.Set.about.aboutBean;
import com.hx.stevenbase.ui.Set.about.aboutDto;
import com.hx.stevenbase.ui.Set.home.homeBean;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    /**
     * 登录
     * @param loginDto
     * @return
     */
    @POST("/api-customer/center/login")
    Call<LoginBean> login(@Body LoginDto loginDto);
    /**
     * about
     * @return
     */
    @POST("/api-customer/center/login")
    Observable<aboutBean> about(@Body aboutDto about);


    /**
     * 首页数据
     * http://www.wanandroid.com/article/list/0/json
     *
     * @param page page
     */
    @GET("/article/list/{page}/json")
    Observable<homeBean> getHomeArticles(@Path("page") int page);
}

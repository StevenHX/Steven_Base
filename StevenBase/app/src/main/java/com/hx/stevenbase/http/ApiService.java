package com.hx.stevenbase.http;

import com.hx.stevenbase.ui.Login.LoginBean;
import com.hx.stevenbase.ui.Login.LoginDto;
import com.hx.stevenbase.ui.Set.about.aboutBean;
import com.hx.stevenbase.ui.Set.about.aboutDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

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
    Call<aboutBean> about(@Body aboutDto about);
}

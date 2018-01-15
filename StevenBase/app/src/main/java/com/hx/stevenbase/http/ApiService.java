package com.hx.stevenbase.http;

import com.hx.stevenbase.ui.Login.LoginBean;
import com.hx.stevenbase.ui.Login.LoginDto;

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
}

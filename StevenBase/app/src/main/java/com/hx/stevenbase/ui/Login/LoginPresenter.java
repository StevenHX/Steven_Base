package com.hx.stevenbase.ui.Login;

import com.hx.steven.http.BaseBean;
import com.hx.stevenbase.http.Api;
import com.hx.stevenbase.http.HttpCallback;

import retrofit2.Call;

/**
 * Created by user on 2018/1/15.
 */

public class LoginPresenter extends LoginContract.Presenter {
    @Override
    void loginRequest(LoginDto loginDto) {
        Api.getInstance().getApiService().login(loginDto).enqueue(new HttpCallback<LoginBean>() {
            @Override
            public void onSuccess(LoginBean result) {

            }

            @Override
            public void onFail(Call<BaseBean<LoginBean>> call, Throwable t) {

            }
        });
    }
}

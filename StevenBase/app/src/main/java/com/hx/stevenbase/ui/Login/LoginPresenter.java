package com.hx.stevenbase.ui.Login;

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
                getMvpView().loginSuccess(result);
            }

            @Override
            public void onFail(Call<LoginBean> call, Throwable t) {
                getMvpView().loginFail(t.getMessage());
            }
        });
    }
}

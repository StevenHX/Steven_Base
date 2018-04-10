package com.hx.stevenbase.ui.Login;


import com.hx.steven.http.BaseBean;
import com.hx.steven.util.ToastUtil;
import com.hx.stevenbase.app.App;
import com.hx.stevenbase.http.Api;
import com.hx.stevenbase.http.HttpCallback;

import retrofit2.Call;

/**
 *
 * Created by huangxiao on 2018/4/9.
 */

public class LoginModel implements LoginContract.Model {


    @Override
    public boolean doCheck(LoginDto loginDto) {
       String mobile = loginDto.getMobile();
       String pwd = loginDto.getPassword();
       if(mobile == null || mobile.length()==0) {
           ToastUtil.showToast(App.getAppContext(),"手机号码为空");
           return false;
       }
       if(pwd == null || pwd.length() == 0) {
           ToastUtil.showToast(App.getAppContext(),"密码为空");
           return false;
       }
        return true;
    }

    @Override
    public void doLogin(LoginDto loginDto, final LoginListener listener) {
        Api.getInstance().getApiService().login(loginDto).enqueue(new HttpCallback<LoginBean>() {
            @Override
            public void onSuccess(LoginBean result) {
                listener.loginSuccess(result);

            }

            @Override
            public void onFail(Call<BaseBean<LoginBean>> call, Throwable t) {
                listener.loginFail(t);
            }
        });
    }
}

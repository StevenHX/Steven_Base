package com.hx.stevenbase.ui.Login;

/**
 * Created by huangxiao on 2018/4/10.
 */

public interface LoginListener {
    void loginSuccess(LoginBean loginBean);
    void loginFail(Throwable t);
}

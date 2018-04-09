package com.hx.stevenbase.ui.Login;

import com.hx.steven.Mvp.BaseMvpModel;
import com.hx.steven.Mvp.BaseMvpPresenter;
import com.hx.steven.Mvp.BaseMvpView;

/**
 *
 * Created by user on 2018/1/15.
 */

public interface LoginContract {

    interface View extends BaseMvpView {
        void loginSuccess(LoginBean loginBean);
        void loginFail(String msg);
    }
    abstract class Presenter extends BaseMvpPresenter<View> {
        abstract void loginRequest(LoginDto loginDto);
    }
    interface Model extends BaseMvpModel {
        void doLogin(LoginBean loginBean);
    }
}

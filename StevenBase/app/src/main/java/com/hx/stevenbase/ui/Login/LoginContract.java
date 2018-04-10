package com.hx.stevenbase.ui.Login;

import com.hx.steven.Mvp.BaseMvpModel;
import com.hx.steven.Mvp.BaseMvpPresenter;
import com.hx.steven.Mvp.BaseMvpView;

/**
 *
 * Created by user on 2018/1/15.
 */

public interface LoginContract {
    /**activity或者fragment实现，负责UI的展示*/
    interface View extends BaseMvpView {
        void loginSuccess(LoginBean loginBean);
        void loginFail(String msg);
    }
    /**只进行各种逻辑的判断，调用model和view的方法*/
    abstract class Presenter extends BaseMvpPresenter<LoginActivity> {
        abstract void loginRequest(LoginDto loginDto);
    }
    /**进行具体的数据操作*/
    interface Model extends BaseMvpModel {
        boolean doCheck(LoginDto loginDto);
        void doLogin(LoginDto loginDto,LoginListener listener);
    }
}

package com.hx.stevenbase.ui.Login;

/**
 * Created by user on 2018/1/15.
 */

public class LoginPresenter extends LoginContract.Presenter {

    @Override
    void loginRequest(LoginDto loginDto) {
        if (!getModel(LoginModel.class).doCheck(loginDto)) {
            return;
        }
        getMvpView().showLoding("");
        getMvpView(LoginActivity.class).getMultipleStatusView().showLoading();
        getModel(LoginModel.class).doLogin(loginDto, new LoginListener() {
            @Override
            public void loginSuccess(LoginBean loginBean) {
                getMvpView().dismissLoding();
                getMvpView(LoginActivity.class).getMultipleStatusView().showContent();
                getMvpView().loginSuccess(loginBean);
            }

            @Override
            public void loginFail(Throwable t) {
                getMvpView().dismissLoding();
                getMvpView(LoginActivity.class).getMultipleStatusView().showError();
                getMvpView().loginFail(t.getMessage());
            }
        });
    }
}

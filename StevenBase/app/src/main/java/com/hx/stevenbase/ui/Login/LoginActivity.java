package com.hx.stevenbase.ui.Login;

import android.widget.Button;
import android.widget.ImageView;

import com.hx.steven.activity.BaseMvpActivity;
import com.hx.steven.component.ClearEditText;
import com.hx.stevenbase.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseMvpActivity<LoginPresenter, LoginContract.View> implements LoginContract.View {


    @BindView(R.id.login_icon)
    ImageView loginIcon;
    @BindView(R.id.login_pwd)
    ClearEditText loginPwd;
    @BindView(R.id.login_name)
    ClearEditText loginName;
    @BindView(R.id.login_ok)
    Button loginOk;

    private LoginDto loginDto;
    @Override
    protected int getContentId() {
        return R.layout.activity_login;
    }

    @Override
    protected boolean isShowHeader() {
        return true;
    }

    @Override
    public void initMvpView() {
        ButterKnife.bind(this);
        setTitle("登录");
        loginDto = new LoginDto();
    }

    @Override
    public void loginSuccess(LoginBean loginBean) {
        getMultipleStatusView().showContent();
    }

    @Override
    public void loginFail(String msg) {
        getMultipleStatusView().showError();
    }

    @Override
    public LoginPresenter creatPresenter() {
        return new LoginPresenter();
    }


    @OnClick(R.id.login_ok)
    public void onViewClicked() {
        loginDto.setMobile(loginName.toString());
        loginDto.setPassword(loginPwd.toString());
        loginDto.setSimulator(false);
        getMultipleStatusView().showLoading();
        mPresenter.loginRequest(loginDto);
    }
}

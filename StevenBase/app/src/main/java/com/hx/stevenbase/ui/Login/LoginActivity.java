package com.hx.stevenbase.ui.Login;

import android.widget.Button;
import android.widget.ImageView;

import com.hx.steven.Mvp.BaseMvpModel;
import com.hx.steven.activity.BaseMvpActivity;
import com.hx.steven.component.ClearEditText;
import com.hx.stevenbase.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseMvpActivity<LoginPresenter> implements LoginContract.View {
    {
        setEnableHeader(true);
        setEnableMultiple(true);
    }

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
    public void loginSuccess(LoginBean loginBean) {

    }

    @Override
    public void loginFail(String msg) {
        
    }

    @Override
    public void initMvpView() {
        ButterKnife.bind(this);
        setHeaderNormal("登陆","返回",null);
        loginDto = new LoginDto();
        loginName.setShakeAnimation();
    }

    @Override
    public LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    @Override
    public List<BaseMvpModel> createModels() {
        List<BaseMvpModel> models = new ArrayList<>();
        models.add(new LoginModel());
        return models;
    }

    @OnClick(R.id.login_ok)
    public void onViewClicked() {
        loginDto.setMobile(loginName.getText().toString());
        loginDto.setPassword(loginPwd.getText().toString());
        loginDto.setSimulator(false);

        mPresenter.loginRequest(loginDto);
    }
}

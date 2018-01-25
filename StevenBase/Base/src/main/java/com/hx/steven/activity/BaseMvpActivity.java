package com.hx.steven.activity;

import com.hx.steven.Mvp.BaseMvpView;
import com.hx.steven.Mvp.Presenter;
import com.hx.steven.util.ToastUtil;

public abstract class BaseMvpActivity<P extends Presenter<V>,V extends BaseMvpView> extends BaseActivity implements BaseMvpView{
    public P mPresenter;

    @Override
    public void showLoding(String msg) {
        showProgressDialog(msg);
    }

    @Override
    public void dismissLoding() {
        dismissProgressDialog();
    }

    @Override
    public void showErr(String err) {
        ToastUtil.showToast(this,err);
    }

    @Override
    protected void initView() {
        mPresenter = creatPresenter();
        if(mPresenter!=null) mPresenter.attachView((V)this);
        initMvpView();
    }

    public abstract P creatPresenter();
    public abstract void initMvpView();
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mPresenter!=null) mPresenter.detachView();
    }
}
package com.hx.steven.Mvp;

import com.hx.steven.activity.BaseActivity;
import com.hx.steven.util.ToastUtil;

import java.util.List;

public abstract class BaseMvpActivity<P extends BaseMvpPresenter> extends BaseActivity implements BaseMvpView {
    public P mPresenter;

    public abstract void initMvpView();

    public abstract P createPresenter();

    public abstract List<BaseMvpModel> createModels();

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
        ToastUtil.showToast(this, err);
    }

    @Override
    protected void initView() {
        mPresenter = createPresenter();
        if (mPresenter != null) {
            mPresenter.attachView(this);
            mPresenter.attachModels(createModels());
        }
        initMvpView();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter.unScribe();
            mPresenter.detachModel();
        }
    }
}

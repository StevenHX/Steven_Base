package com.hx.steven.fragment;

import android.support.v4.app.Fragment;
import android.view.View;

import com.hx.steven.Mvp.BaseMvpView;
import com.hx.steven.Mvp.Presenter;
import com.hx.steven.util.ToastUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseMvpLazyFragment<P extends Presenter<V>,V extends BaseMvpView> extends BaseLazyFragment implements BaseMvpView {

    protected P mPresenter;
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
        ToastUtil.showToast(context,err);
    }
    @Override
    protected void initView(View view) {
        mPresenter = createPresenter();
        if(mPresenter!=null) mPresenter.attachView((V)this);
        initMvpLazyFragment(view);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mPresenter!=null) {
            mPresenter.detachView();
            mPresenter.unScribe();//解除Rxjava观察者和被观察者订阅关系
        }
    }

    protected abstract void initMvpLazyFragment(View view);
    protected abstract P createPresenter();
}

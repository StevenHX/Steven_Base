package com.hx.steven.fragment;

import androidx.fragment.app.Fragment;
import android.view.View;

import com.hx.steven.Mvp.BaseMvpModel;
import com.hx.steven.Mvp.BaseMvpPresenter;
import com.hx.steven.Mvp.BaseMvpView;
import com.hx.steven.util.ToastUtil;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseMvpLazyFragment<P extends BaseMvpPresenter> extends BaseLazyFragment implements BaseMvpView {

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
        if(mPresenter!=null) {
            mPresenter.attachView(this);
            mPresenter.attachModels(createModels());
        }
        initMvpLazyFragment(view);
    }

    @Override
    public void onDestroy() {
        if(mPresenter!=null) {
            mPresenter.detachView();
            mPresenter.unScribe();//解除Rxjava观察者和被观察者订阅关系
            mPresenter.detachModel();
        }
        super.onDestroy();
    }

    protected abstract void initMvpLazyFragment(View view);
    protected abstract P createPresenter();
    public abstract List<BaseMvpModel> createModels();

}

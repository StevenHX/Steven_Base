package com.hx.steven.Mvvm;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.hx.steven.component.MProgressDialog;

public abstract class BaseMVVMActivity<V extends ViewDataBinding, M extends ViewModel> extends AppCompatActivity {
    private V mBinding;
    private M mViewModel;

    private MProgressDialog mProgressDialog;

    protected abstract int getContentId();
    public abstract Class<M> getViewModelClass();

    public V getmBinding() {
        return mBinding;
    }

    public M getmViewModel() {
        return mViewModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, getContentId());
        mViewModel = new ViewModelProvider(
                this, new ViewModelProvider.AndroidViewModelFactory(getApplication())
        ).get(getViewModelClass());
    }

    /**
     * 显示dialog
     */
    public void showProgressDialog(String msg) {
        if (mProgressDialog == null) {
            mProgressDialog = new MProgressDialog.Builder(this)
                    .build();
        }
        mProgressDialog.show(msg);
    }

    /**
     * 隐藏dialog
     */
    public void dismissProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    /**
     * 开启activity
     */
    public void launch(Context mContext, Bundle bundle, Class clazz) {
        Intent mIntent = new Intent(mContext, clazz);
        mIntent.putExtras(bundle);
        launch(mContext, mIntent);
    }

    /**
     * 开启activity
     */
    public void launch(Context mContext, Intent intent) {
        mContext.startActivity(intent);
    }

    /**
     * 开启activity
     */
    public void launch(Context mContext, Class clazz) {
        Intent mIntent = new Intent(mContext, clazz);
        launch(mContext, mIntent);
    }

    /**
     * 开启activity
     */
    public void launch_FLAGS(Context mContext, Class clazz, int FLAGS) {
        Intent intent = new Intent(mContext, clazz);
        intent.addFlags(FLAGS);
        mContext.startActivity(intent);
    }

    /**
     * 打开activity并且获取回调
     */
    public void launchForResult(Activity activity, Bundle bundle, Class clazz, int requestCode) {
        Intent mIntent = new Intent(activity, clazz);
        mIntent.putExtras(bundle);
        activity.startActivityForResult(mIntent, requestCode);
    }

    /**
     * 打开activity并且获取回调
     */
    public void launchForResult_FLAGS(Activity activity, Bundle bundle, Class clazz, int requestCode, int FLAGS) {
        Intent mIntent = new Intent(activity, clazz);
        mIntent.putExtras(bundle);
        mIntent.addFlags(FLAGS);
        activity.startActivityForResult(mIntent, requestCode);
    }

}

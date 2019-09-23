package com.hx.steven.baseView;

import androidx.appcompat.app.AppCompatActivity;

import com.hx.steven.component.MProgressDialog;

/**
 * activity基础实现类
 *
 * @author haungxiao
 */
public class BaseActivity extends AppCompatActivity implements IBaseView {

    /**
     * Loading提示
     */
    private MProgressDialog mProgressDialog;

    @Override
    public void showLoading(String msg) {
        if (mProgressDialog == null) {
            mProgressDialog = new MProgressDialog.Builder(this)
                    .build();
        }
        mProgressDialog.show(msg);
    }

    @Override
    public void dismissLoading() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }
}

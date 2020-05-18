package com.hx.steven.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.hx.steven.R;
import com.hx.steven.component.MProgressDialog;
import com.hx.steven.component.MultipleStatusView;
import com.hx.steven.util.MPermissionUtil;

public abstract class BaseActivity extends AppCompatActivity {
    /**
     * 内容视图的params
     */
    LinearLayout.LayoutParams contentParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1);
    /**
     * 容器视图
     */
    private ViewGroup mContainer;
    /**
     * progressDialog
     */
    private MProgressDialog mProgressDialog;
    /**
     * 内容多状态视图
     */
    private MultipleStatusView multipleStatusView;
    /**
     * 是否显示MultipleView
     */
    private boolean enableMultiple;

    private static BaseActivity baseActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);//设置屏幕保持竖直
        initContainer();
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        baseActivity = null;
    }

    private void initContainer() {
        setContentView(R.layout.base_activity);
        mContainer = (ViewGroup) findViewById(R.id.base_container);

        baseActivity = this;
        /**初始化内容视图*/
        View layout = LayoutInflater.from(this).inflate(getContentId(), null);
        layout.setLayoutParams(contentParams);

        if (enableMultiple) {
            /**初始化多状态视图*/
            multipleStatusView = new MultipleStatusView(this);
            multipleStatusView.setLayoutParams(contentParams);
            multipleStatusView.addView(layout);
            mContainer.addView(multipleStatusView);
        } else {
            mContainer.addView(layout);
        }
    }

    protected abstract void initView();

    protected abstract int getContentId();

    /**
     * 6.0权限
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        MPermissionUtil.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * 设置需要获取的权限
     */
    public void RequestPermissions(int requestCode, String... permissions) {
        MPermissionUtil.requestPermissionsResult(this, requestCode, permissions, new MPermissionUtil.OnPermissionListener() {
            @Override
            public void onPermissionGranted() {
                permissionsGrant();
            }

            @Override
            public void onPermissionDenied() {
                permissionDenied();
            }
        });
    }

    protected void permissionsGrant() {
    }

    /**
     * ===================================================  设置公共方法=============================
     */

    protected void permissionDenied() {
    }

    /**
     * 获取multipleView
     *
     * @return
     */
    public MultipleStatusView getMultipleStatusView() {
        if (!enableMultiple) {
            throw new RuntimeException("noMultipleView");
        }
        return multipleStatusView;
    }

    /**
     * 设置是否显示multipleView
     */
    public void setEnableMultiple(boolean enableMultiple) {
        this.enableMultiple = enableMultiple;
    }

    /**
     * 获取当前实例
     *
     * @return
     */
    public static BaseActivity getThis() {
        return baseActivity;
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

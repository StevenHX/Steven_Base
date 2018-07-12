package com.hx.steven.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.hx.steven.R;
import com.hx.steven.component.HeaderView;
import com.hx.steven.component.MProgressDialog;
import com.hx.steven.component.MultipleStatusView;
import com.hx.steven.util.MPermissionUtil;

public abstract class BaseActivity extends SlidingActivity {
    /**
     * 内容视图的params
     */
    LinearLayout.LayoutParams contentParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1);
    /**
     * 头部视图的params
     */
    LinearLayout.LayoutParams headParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
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
     * 头部视图
     */
    private HeaderView headerView;
    /**
     * 是否显示头部
     */
    private boolean enableHeader;
    /**
     * 是否显示MultipleView
     */
    private boolean enableMultiple;

    protected abstract void initView();
    protected abstract int getContentId();
    protected void clickHeadLeft() {
        onBackPressed();
    }
    protected void clickHeadRight() {
    }
    protected void permissionsGrant(){}
    protected void permissionDenied(){}


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);//设置屏幕保持竖直
        initContainer();
        setInside();
        excuteStatesBar();
        initView();
    }

    private void initContainer() {
        setContentView(R.layout.base_activity);
        mContainer = (ViewGroup) findViewById(R.id.base_container);

        /**初始化多状态视图*/
        multipleStatusView = new MultipleStatusView(this);
        multipleStatusView.setLayoutParams(contentParams);
        /**初始化内容视图*/
        View layout = LayoutInflater.from(this).inflate(getContentId(), null);
        layout.setLayoutParams(contentParams);
        /**初始化头部视图*/
        headerView = new HeaderView(this);
        headerView.setLayoutParams(headParams);

        /**---添加视图逻辑----*/
        if (enableHeader) {
            mContainer.addView(headerView);
        }
        if (enableMultiple) {
            multipleStatusView.addView(layout);
            mContainer.addView(multipleStatusView);
        } else {
            mContainer.addView(layout);
        }
    }

    /**
     * 设置沉浸式效果
     */
    private void setInside() {
        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
    }

    /**
     * 解决4.4设置状态栏颜色之后，布局内容嵌入状态栏位置问题
     */
    private void excuteStatesBar() {
        ViewGroup mContentView = (ViewGroup) getWindow().findViewById(Window.ID_ANDROID_CONTENT);
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
            //注意不是设置 ContentView 的 FitsSystemWindows,
            // 而是设置 ContentView 的第一个子 View ，预留出系统 View 的空间.
            mChildView.setFitsSystemWindows(true);
        }
    }




    /**
     * 6.0权限
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        MPermissionUtil.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

/**
 * ===================================================  设置公共方法=============================
 */

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
     * 设置是否显示headView
     */
    public void setEnableHeader(boolean enableHeader) {
        this.enableHeader = enableHeader;
    }

    /**
     * 设置header
     *
     * @param title
     * @param leftString
     * @param rightString
     */
    public void setHeaderNormal(String title, String leftString, String rightString) {
        if (!enableHeader) {
            throw new RuntimeException("noHeaderView");
        }
        getHeaderView().setTitleString(title);
        getHeaderView().setLeftString(leftString);
        getHeaderView().setRightString(rightString);
        getHeaderView().setOnHeadClickListener(new HeaderView.OnHeadClickListener() {
            @Override
            public void ClickLeft() {
                clickHeadLeft();
            }

            @Override
            public void ClickRight() {
                clickHeadRight();
            }
        });
    }

    /**
     * 获取headView
     *
     * @return
     */
    public HeaderView getHeaderView() {
        return enableHeader ? headerView : null;
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

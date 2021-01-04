package com.hx.steven.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;
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
    /**
     * 骨架屏
     */
    private SkeletonScreen skeletonScreen;
    /**
     * 是否使用骨架屏
     */
    private boolean enableSkeletonScreen;
    /**
     * 骨架屏view
     */
    private View skeletonView;
    /**
     * 骨架屏recycle view
     */
    private RecyclerView skeletonRecyclerview;
    /**
     * 骨架屏recycle adapter
     */
    private RecyclerView.Adapter skeletonAdapter;

    /**
     * 显示骨架
     */
    private int skeletonLayout;

    @SuppressLint("StaticFieldLeak")
    private static BaseActivity baseActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);//设置屏幕保持竖直
        initContainer();
        initView();
        if (enableSkeletonScreen) this.initSkeleton();
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

    private void initSkeleton() {
        if (skeletonRecyclerview != null && skeletonAdapter != null) {
            skeletonScreen = Skeleton.bind(skeletonRecyclerview)
                    .adapter(skeletonAdapter)//设置实际adapter
                    .shimmer(true)//是否开启动画
                    .angle(30)//shimmer的倾斜角度
                    .frozen(true)//true则表示显示骨架屏时，RecyclerView不可滑动，否则可以滑动
                    .duration(1200)//动画时间，以毫秒为单位
                    .count(10)//显示骨架屏时item的个数
                    .load(this.skeletonLayout == 0 ? R.layout.default_skeleton_news_item : this.skeletonLayout)//骨架屏UI
                    .show(); //default count is 1
        } else {
            if (skeletonView == null) try {
                throw new Exception("未设置骨架屏View");
            } catch (Exception e) {
                e.printStackTrace();
            }
            skeletonScreen = Skeleton.bind(skeletonView)
                    .shimmer(true)//是否开启动画
                    .angle(30)//shimmer的倾斜角度
                    .duration(1200)//动画时间，以毫秒为单位
                    .load(this.skeletonLayout == 0 ? R.layout.default_skeleton_news_item : this.skeletonLayout)//骨架屏UI
                    .show(); //default count is 1
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
     * 设置是否显示骨架屏
     *
     * @param enableSkeleton
     */
    public void setEnableSkeletonScreen(boolean enableSkeleton, RecyclerView recyclerView, RecyclerView.Adapter adapter) {
        this.enableSkeletonScreen = enableSkeleton;
        this.skeletonRecyclerview = recyclerView;
        this.skeletonAdapter = adapter;
    }

    public void setEnableSkeletonScreen(boolean enableSkeleton, View view) {
        this.enableSkeletonScreen = enableSkeleton;
        this.skeletonView = view;
    }

    /**
     * 设置显示骨架(需要在setEnableSkeletonScreen之前设置才有效)
     *
     * @param skeletonLayout
     */
    public void setSkeletonLayout(@LayoutRes int skeletonLayout) {
        this.skeletonLayout = skeletonLayout;
    }

    /**
     * 获取骨架屏对象
     *
     * @return
     */
    public void hideSkeletonScreen() throws Exception {
        if (skeletonScreen == null) throw  new Exception("skeletonScreen为空");
        skeletonScreen.hide();
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

package com.hx.steven.fragment;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.hx.steven.R;
import com.hx.steven.activity.BaseActivity;
import com.hx.steven.component.MultipleStatusView;
import com.hx.steven.util.MPermissionUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseFragment extends Fragment {
    /**
     * 内容视图的params
     */
    LinearLayout.LayoutParams contentParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT,1);
    /**
     * 上下文对象
     */
    public Context context;
    /**
     * 获取该fragment依附的activity
     */
    public Activity activity;
    /**
     * 容器视图
     */
    private ViewGroup mContainer;
    /**
     * 内容多状态视图
     */
    private MultipleStatusView multipleStatusView;
    /**
     * 是否显示MultipleView
     */
    private boolean enableMultiple;

    protected abstract int getContentId();
    protected abstract void initView(View view);
    protected void permissionsGrant(){}
    protected void permissionDenied(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
        activity = getActivity();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.base_fragmemt, container, false);
        mContainer = view.findViewById(R.id.base_container);
        /**初始化multipleView*/
        multipleStatusView = new MultipleStatusView(context);
        multipleStatusView.setLayoutParams(contentParams);
        /**初始化内容视图*/
        View layout = inflater.inflate(getContentId(), null);
        layout.setLayoutParams(contentParams);
        /**-----视图添加逻辑-----*/
        if(enableMultiple){
            multipleStatusView.addView(layout);
            mContainer.addView(multipleStatusView);
        }else{
            mContainer.addView(layout);
        }
        initView(view);
        return view;
    }

    /**
     * 6.0权限回调
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        MPermissionUtil.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * =====================================设置公共方法============================
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
    public MultipleStatusView getMultipleStatusView() {
        if(!enableMultiple){
            throw new RuntimeException("noMultipleView");
        }
        return  multipleStatusView;
    }

    /**
     * 设置是否显示MultipleView
     * @param enableMultiple
     */
    public void setEnableMultiple(boolean enableMultiple){
        this.enableMultiple = enableMultiple;
    }
    /**
     * 显示dialog
     */
    public void showProgressDialog(String msg) {
        if (activity instanceof BaseActivity) {
            ((BaseActivity) activity).showProgressDialog(msg);
        }
    }

    /**
     * 隐藏dialog
     */
    public void dismissProgressDialog() {
        if (activity instanceof BaseActivity) {
            ((BaseActivity) activity).dismissProgressDialog();
        }
    }

}

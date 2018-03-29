package com.hx.steven.fragment;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hx.steven.R;
import com.hx.steven.activity.BaseActivity;
import com.hx.steven.component.MultipleStatusView;
import com.hx.steven.util.MPermissionUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseFragment extends Fragment implements View.OnClickListener {
    public Context context;
    public Activity activity;
    private MultipleStatusView multipleStatusView;//内容多状态视图
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
        activity =getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmemt_base,container,false);
        multipleStatusView = view.findViewById(R.id.base_frag_multipleView);
        View layout = inflater.inflate(getContentId(),null);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layout.setLayoutParams(params);
        multipleStatusView.addView(layout);
        multipleStatusView.setOnRetryClickListener(this);
        initView(view);
        return view;
    }


    protected abstract void initView(View view);
    protected abstract int getContentId();
    /**
     *6.0权限
    */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        MPermissionUtil.onRequestPermissionsResult(requestCode,permissions,grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     *=====================================设置公共方法============================
     */
    public MultipleStatusView getMultipleStatusView()
    {
        return multipleStatusView;
    }

    public  BaseFragment setargs(Bundle args) {
        setArguments(args);
        return this;
    }

    /**
     * 显示dialog
     */
    public void showProgressDialog(String msg){
       if(activity instanceof BaseActivity){
           ((BaseActivity)activity).showProgressDialog(msg);
       }
    }

    /**
     * 隐藏dialog
     */
    public void dismissProgressDialog(){
        if(activity instanceof BaseActivity){
            ((BaseActivity)activity).dismissProgressDialog();
        }
    }

    /**
     * 点击加载错误重试
     * @param view
     */
    @Override
    public void onClick(View view) {

    }
}

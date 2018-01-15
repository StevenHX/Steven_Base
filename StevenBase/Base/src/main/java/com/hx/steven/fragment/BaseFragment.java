package com.hx.steven.fragment;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hx.steven.R;
import com.hx.steven.activity.BaseActivity;
import com.hx.steven.component.MultipleStatusView;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseFragment extends Fragment {
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
        multipleStatusView.addView(layout);
        initView(view);
        return view;
    }

    protected abstract void initView(View view);
    protected abstract int getContentId();

    /**
     *=====================================设置公共方法============================
     */
    public MultipleStatusView getMultipleStatusView()
    {
        return multipleStatusView;
    }
}

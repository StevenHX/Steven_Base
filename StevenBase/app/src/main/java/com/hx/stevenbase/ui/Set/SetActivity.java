package com.hx.stevenbase.ui.Set;

import com.hx.steven.activity.BaseActivity;
import com.hx.steven.component.CommViewPager;
import com.hx.stevenbase.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SetActivity extends BaseActivity {


    @BindView(R.id.set_viewpager)
    CommViewPager setViewpager;

    @Override
    protected void initView() {
        setTitle("设置");
        ButterKnife.bind(this);
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_set;
    }

    @Override
    protected boolean isShowHeader() {
        return true;
    }
}

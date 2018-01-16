package com.hx.stevenbase.ui.Set;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.hx.steven.activity.BaseActivity;
import com.hx.steven.component.CommViewPager;
import com.hx.stevenbase.R;
import com.hx.stevenbase.ui.Set.about.aboutFragment;
import com.hx.stevenbase.ui.Set.question.questionFragment;
import com.hx.stevenbase.ui.Set.talk.talkFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SetActivity extends BaseActivity {
    private ArrayList<Fragment> mFragments = new ArrayList<Fragment>();

    @BindView(R.id.set_viewpager)
    CommViewPager setViewpager;

    @Override
    protected void initView() {
        setTitle("设置");
        ButterKnife.bind(this);
      mFragments.add(new aboutFragment());
      mFragments.add(new questionFragment());
      mFragments.add(new talkFragment());
      setViewpager.setAdapter(new SetPageAdapter(getSupportFragmentManager(),mFragments));
      setViewpager.setOffscreenPageLimit(3);
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

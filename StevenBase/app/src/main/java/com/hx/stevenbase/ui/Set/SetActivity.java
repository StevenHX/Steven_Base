package com.hx.stevenbase.ui.Set;

import android.support.v4.app.Fragment;
import com.hx.steven.activity.BaseActivity;
import com.hx.steven.component.BottomBar.BottomBarItem;
import com.hx.steven.component.BottomBar.BottomBarLayout;
import com.hx.steven.component.CommViewPager;
import com.hx.stevenbase.R;
import com.hx.stevenbase.ui.Set.about.aboutFragment;
import com.hx.stevenbase.ui.Set.home.homeFragment;
import com.hx.stevenbase.ui.Set.me.MeFragment;
import com.hx.stevenbase.ui.Set.talk.talkFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SetActivity extends BaseActivity implements BottomBarLayout.OnItemSelectedListener {
    private ArrayList<Fragment> mFragments = new ArrayList<Fragment>();

    @BindView(R.id.set_viewpager)
    CommViewPager setViewpager;
    @BindView(R.id.set_bbl)
    BottomBarLayout bottomBarLayout;

    @Override
    protected void initView() {
        setTitle("设置");
        ButterKnife.bind(this);
      mFragments.add(new aboutFragment());
      mFragments.add(new homeFragment());
      mFragments.add(new talkFragment());
      mFragments.add(new MeFragment());
      setViewpager.setAdapter(new SetPageAdapter(getSupportFragmentManager(),mFragments));
     bottomBarLayout.setViewPager(setViewpager);
     bottomBarLayout.setOnItemSelectedListener(this);
     bottomBarLayout.setUnread(1,99);
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_set;
    }

    @Override
    protected boolean isShowHeader() {
        return true;
    }

    @Override
    public void onItemSelected(BottomBarItem bottomBarItem, int position) {

    }
}

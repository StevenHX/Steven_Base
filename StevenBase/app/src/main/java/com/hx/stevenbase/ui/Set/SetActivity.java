package com.hx.stevenbase.ui.Set;

import androidx.fragment.app.Fragment;
import com.hx.steven.activity.BaseActivity;
import com.hx.steven.component.BottomBar.BottomBarItem;
import com.hx.steven.component.BottomBar.BottomBarLayout;
import com.hx.steven.component.NoScrollViewPager;
import com.hx.stevenbase.R;
import com.hx.stevenbase.ui.Set.about.aboutFragment;
import com.hx.stevenbase.ui.Set.home.homeFragment;
import com.hx.stevenbase.ui.Set.me.MeFragment;
import com.hx.stevenbase.ui.Set.talk.talkFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SetActivity extends BaseActivity implements BottomBarLayout.OnItemSelectedListener {
    {
        setEnableMultiple(false);
    }
    @BindView(R.id.set_viewpager)
    NoScrollViewPager setViewpager;

    @BindView(R.id.set_bbl)
    BottomBarLayout bottomBarLayout;

    private ArrayList<Fragment> mFragments = new ArrayList<Fragment>();

    @Override
    protected void initView() {
        ButterKnife.bind(this);
      mFragments.add(new aboutFragment());
      mFragments.add(new homeFragment());
      mFragments.add(new talkFragment());
      mFragments.add(new MeFragment());
      setViewpager.setAdapter(new SetPageAdapter(getSupportFragmentManager(),mFragments));
      setViewpager.setOffscreenPageLimit(4);
      setViewpager.setNoScroll(true);
     bottomBarLayout.setViewPager(setViewpager);
     bottomBarLayout.setOnItemSelectedListener(this);
     bottomBarLayout.setUnread(1,99);
    }

    @Override
    protected int getContentId() {
        return R.layout.set_activity;
    }


    @Override
    public void onItemSelected(BottomBarItem bottomBarItem, int position) {

    }
}

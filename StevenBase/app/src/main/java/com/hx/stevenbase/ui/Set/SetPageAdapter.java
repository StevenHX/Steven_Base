package com.hx.stevenbase.ui.Set;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by user on 2018/1/16.
 */

public class SetPageAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> mFragments;
    public SetPageAdapter(FragmentManager fm,ArrayList<Fragment> mFragments) {
        super(fm);
        this.mFragments = mFragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}

package com.hx.steven.component.BannerView;

import androidx.viewpager.widget.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xiejq on 2018/3/21.
 */

public class ViewPagerAdapter extends PagerAdapter {
    private List<View> vp_views;

    public ViewPagerAdapter(List<View> views) {
        if (views == null){
            vp_views = new ArrayList<View>();
        }else {
            vp_views = views;
        }
    }

    @Override
    public int getCount() {
        return vp_views.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View v=vp_views.get(position);
        ViewGroup parent = (ViewGroup) v.getParent();
        if (parent != null) {
            parent.removeAllViews();
        }
        container.addView(vp_views.get(position));
        return vp_views.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(vp_views.get(position));
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

}

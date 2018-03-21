package com.hx.steven.component.CircularViewPager;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by Xiejq on 2018/3/21.
 */

public class CircularPagerAdaper extends PagerAdapter {
    private List<ImageView> list;
    public CircularPagerAdaper(List<ImageView> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    //当list的数量小于等于三时，此处会报异常，此处把异常catch到不处理，然后destroyItem不移除
    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        try {
            container.addView(list.get(position % list.size()));
        } catch (Exception e) {
        }
        return list.get(position % list.size());
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (list.size() > 3) {
            container.removeView(list.get(position % list.size()));
        }
    }
}

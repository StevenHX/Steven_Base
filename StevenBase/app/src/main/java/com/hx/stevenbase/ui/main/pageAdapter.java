package com.hx.stevenbase.ui.main;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by Steven on 2018/1/28.
 */

public class pageAdapter extends PagerAdapter {
    private int[] images;
    private Context context;
    public pageAdapter(Context context,int[] images) {
       this.images = images;
       this.context = context;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ViewGroup.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        ImageView view = new ImageView(context);
        view.setImageResource(images[position]);
        view.setLayoutParams(layoutParams);
        view.setScaleType(ImageView.ScaleType.FIT_XY);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}

package com.hx.steven.component;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 禁止左右滑动的viewpager
 * Created by user on 2017/10/31.
 */

public class CommViewPager extends ViewPager {
    private boolean noScroll = false;
    public void setNoScroll(boolean noScroll) {
        this.noScroll = noScroll;
    }
    public CommViewPager(Context context) {
        super(context);
    }

    public CommViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (noScroll)
            return false;
        else
            return super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (noScroll)
            return false;
        else
            return super.onInterceptTouchEvent(ev);
    }
}

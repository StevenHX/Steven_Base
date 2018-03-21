package com.hx.steven.component.CircularViewPager;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by hong on 2016/8/21.
 */
public class CircularViewpager extends ViewPager {
    private static final int START_CIRCULAR = 0;
    private static final int STOP_CIRCULAR = 1;

    private boolean flag = false;
    private int intervalTime = 3 * 1000;


    private OnPagerClickListener onPagerClickListener;
    private int allPagerCount;

    public void setOnPagerClickListener(int allPagerCount, OnPagerClickListener onPagerClickListener) {
        this.allPagerCount = allPagerCount;
        this.onPagerClickListener = onPagerClickListener;
    }

    /**
     * handler实现自动轮询
     */
    private Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case START_CIRCULAR:
                    if (flag) {
                        setCurrentItem(getCurrentItem() + 1);
                        mhandler.sendEmptyMessageDelayed(START_CIRCULAR, intervalTime);
                    }
                    break;
                case STOP_CIRCULAR:
                    mhandler.removeCallbacksAndMessages(null);
                    break;
                default:
                    break;
            }
        }
    };

    public CircularViewpager(Context context) {
        this(context, null);
    }

    public CircularViewpager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 开始自动轮询
     */
    public void start() {
        flag = true;
        mhandler.sendEmptyMessageDelayed(START_CIRCULAR, intervalTime);
    }

    /**
     * 停止自动轮询
     */
    public void stop() {
        mhandler.sendEmptyMessage(STOP_CIRCULAR);
        flag = false;
    }

    /**
     * 设置循环间隔时间
     */
    public void setIntervalTime(int intervalTime) {
        this.intervalTime = intervalTime;
    }

    private long downTime;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downTime = System.currentTimeMillis();
                stop();
                break;
            case MotionEvent.ACTION_UP:
                long upTime = System.currentTimeMillis();
                if ((upTime - downTime) < 100) {
                    if (onPagerClickListener != null) {
                        onPagerClickListener.onPagerClick(getCurrentItem() % allPagerCount);
                    }
                }
                start();
                break;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 点击事件回调接口
     */
    public interface OnPagerClickListener {
        void onPagerClick(int pagerPosition);
    }
}

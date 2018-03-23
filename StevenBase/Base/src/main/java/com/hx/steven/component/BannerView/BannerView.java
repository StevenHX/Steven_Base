package com.hx.steven.component.BannerView;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.hx.steven.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 结合view pager和indicator的banner控件
 *@author huangxiao
 *@date 2018/3/21
 */

public class BannerView extends RelativeLayout {
    private int mPosition = 1;
    private Context mContext;
    public ViewPager vp_banner;
    private LinearLayout ll_indicator;

    //Banner展示的view
    private List<View> vp_views = new ArrayList<View>();
    private List<ImageView> dot_imgs = new ArrayList<ImageView>();

    //指示器图片资源
    private int[] indicatorImgRes = {R.drawable.dot_blue_bg, R.drawable.dot_white_bg};

    //定时任务
    private ScheduledExecutorService scheduledExecutorService;
    //弱引用view pager
    private WeakReference<ViewPager> mWeakVP;
    //定时任务线程
    private SlideShowTask mSlideShowTask;
    //handle
    private MyHandle mHandle;
    //当前item
    private int currentItem;


    public BannerView(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    public BannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    private void initView(){
        /**
         * 用Java代码创建布局
         */
        vp_banner = new ViewPager(mContext);
        LinearLayout.LayoutParams vp_param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        vp_banner.setLayoutParams(vp_param);

        ll_indicator = new LinearLayout(mContext);
        RelativeLayout.LayoutParams dot_param = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        dot_param.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        dot_param.setMargins(0, 0, 0, 10);
        ll_indicator.setLayoutParams(dot_param);
        ll_indicator.setGravity(Gravity.CENTER_HORIZONTAL);

        this.addView(vp_banner);
        this.addView(ll_indicator);
    }

    /**
     * 设置banner展示的view，指示器图片为默认图片
     * @param views banner展示的view
     */
    public void setView(List<View> views){
        setView(views, null);
    }

    /**
     * 设置banner展示的view和指示器图片
     * @param views banner展示的view
     * @param indicatorRes 指示器图片
     */
    public void setView(List<View> views, int[] indicatorRes){
        if (views != null){
            vp_views = views;
        }

        vp_banner.setAdapter(new ViewPagerAdapter(vp_views));
        vp_banner.setOffscreenPageLimit(1);
        vp_banner.setCurrentItem(1);
        vp_banner.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //指示器跳转
                mPosition = position;
                int pageIndex = mPosition;
                if(mPosition == 0){
                    pageIndex = dot_imgs.size();
                }else if(mPosition == dot_imgs.size() + 1){
                    pageIndex = 1;
                }
                setIndicator(pageIndex);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //ViewPager跳转
                int pageIndex = mPosition;
                if(mPosition == 0){
                    pageIndex = dot_imgs.size();
                }else if(mPosition == dot_imgs.size() + 1){
                    pageIndex = 1;
                }
                if (pageIndex != mPosition) {
                    //无滑动动画，直接跳转
                    vp_banner.setCurrentItem(pageIndex, false);
                    return;
                }

            }
        });

        //加载指示器
        if (indicatorRes!=null && indicatorRes.length>=2){
            indicatorImgRes = indicatorRes;
        }
        for (int i = 0; i < vp_views.size(); i++) {
            ImageView imageView = new ImageView(mContext);
            if (i == 0){
                imageView.setBackgroundResource(indicatorImgRes[0]);
            } else {
                imageView.setBackgroundResource(indicatorImgRes[1]);
            }
            dot_imgs.add(imageView);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            int margin_h = (int)(0.016 * mContext.getResources().getDisplayMetrics().widthPixels);
            int margin_v = (int)(0.02 * mContext.getResources().getDisplayMetrics().widthPixels);
            lp.setMargins(margin_h, margin_v, margin_h, margin_v);
            ll_indicator.addView(imageView, lp);
        }
    }

    /**
     * 设置指示器的图片
     * @param position 当前banner位置
     */
    private void setIndicator(int position){
        for (int i = 0; i < dot_imgs.size(); i++) {
            if (position == i+1){
                dot_imgs.get(i).setBackgroundResource(indicatorImgRes[0]);
            } else {
                dot_imgs.get(i).setBackgroundResource(indicatorImgRes[1]);
            }
        }
    }

    /**
     * 开启自动轮播
     * @param period banner轮播的周期
     */
    public void startAutoPlay(long period){
        //banner的vp_views数量大于1时，才允许自动轮播
        if (vp_views.size()>1) {
            if (scheduledExecutorService == null) {
                scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
                if (mSlideShowTask == null) {
                    mSlideShowTask = new SlideShowTask(this);
                    mHandle = new MyHandle(this);
                }
                scheduledExecutorService.scheduleAtFixedRate(mSlideShowTask, 4, 4, TimeUnit.SECONDS);
            }
        }
    }

    /**
     * 关闭自动轮播
     */
    public void stopAutoPlay() {
        if (scheduledExecutorService != null){
            scheduledExecutorService.shutdown();
            mHandle = null;
        }
    }

    /**
     * 轮播任务线程
     */
    private static class SlideShowTask extends Thread {

        private final WeakReference<BannerView> mAutoShowViewWeakReference;

        private SlideShowTask(BannerView bannerView) {
            mAutoShowViewWeakReference = new WeakReference<>(bannerView);
        }

        @Override
        public void run() {
            if (mAutoShowViewWeakReference.get() != null) {
                BannerView bannerView = mAutoShowViewWeakReference.get();
                synchronized (bannerView.vp_banner) {
                    bannerView.currentItem = (bannerView.currentItem + 1) % bannerView.vp_views.size();
                    bannerView.mHandle.obtainMessage().sendToTarget();
                }
            }
        }

    }

    /**
     * Handle
     */
    private static class MyHandle extends Handler{
        private WeakReference<BannerView> mAutoShowViewWeakReference;

        private MyHandle(BannerView bannerView) {
            mAutoShowViewWeakReference = new WeakReference<>(bannerView);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            BannerView bannerView = mAutoShowViewWeakReference.get();
            if (bannerView.vp_banner != null) {
                bannerView.vp_banner.setCurrentItem(bannerView.currentItem);
            }
        }
    }
}

package com.hx.steven.util;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.res.Configuration;
import androidx.annotation.NonNull;
import android.util.DisplayMetrics;

/**
 * 今日头条方案屏幕适配工具类
 * Created by Steven on 2018/11/5.
 */

public class ScreenAdaptationUtil {
    //设计稿宽度dp
    private static final int UISize = 360;
    private static float mNoncompactDensity;
    private static float mNoncompactScaledDensity;

    public static void SetCustomDensity(@NonNull Activity activity, @NonNull final Application application) {
        final DisplayMetrics appDisplayMetrics = application.getResources().getDisplayMetrics();
        if (mNoncompactDensity == 0) {
            mNoncompactDensity = appDisplayMetrics.density;
            mNoncompactScaledDensity = appDisplayMetrics.scaledDensity;
            application.registerComponentCallbacks(new ComponentCallbacks() {
                @Override
                public void onConfigurationChanged(Configuration newConfig) {
                    if (newConfig != null && newConfig.fontScale > 0) {
                        mNoncompactScaledDensity = application.getResources().getDisplayMetrics().scaledDensity;
                    }
                }

                @Override
                public void onLowMemory() {

                }
            });
        }
        final float targetDensity = appDisplayMetrics.widthPixels / UISize;
        final float targetScaledDensity = targetDensity * (mNoncompactScaledDensity / mNoncompactDensity);
        final int targetDensityDpi = (int) (160 * targetDensity);
        appDisplayMetrics.density = targetDensity;
        appDisplayMetrics.scaledDensity = targetScaledDensity;
        appDisplayMetrics.densityDpi = targetDensityDpi;

        final DisplayMetrics activityDisplayMetrics = activity.getResources().getDisplayMetrics();
        activityDisplayMetrics.density = targetDensity;
        activityDisplayMetrics.scaledDensity = targetScaledDensity;
        activityDisplayMetrics.densityDpi = targetDensityDpi;
    }
}

package com.hx.steven.util;

import android.app.Activity;
import android.util.Log;

import com.gyf.barlibrary.ImmersionBar;

public class BarColorUtils {
    private String TAG = "BarColorUtils";

    public static void setBarColor(Activity activity, String color, boolean isDark) {
        try {
            if (color.length() > 0) {
                ImmersionBar.with(activity)
                        .statusBarColor(color)
                        .statusBarDarkFont(isDark, 0.2f)
                        .init();
            } else {
                ImmersionBar.with(activity)
                        .statusBarDarkFont(isDark, 0.2f)
                        .init();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("setBarColor", e.getMessage());
        }
    }
}

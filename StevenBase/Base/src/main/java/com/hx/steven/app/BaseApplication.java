package com.hx.steven.app;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.hx.steven.BuildConfig;
import com.hx.steven.manager.WxManager;
import com.hx.steven.util.ActivityManagerUtil;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.tencent.bugly.Bugly;
import com.tencent.smtt.export.external.TbsCoreSettings;
import com.tencent.smtt.sdk.QbSdk;

import java.util.HashMap;

import cn.jpush.android.api.JPushInterface;


/**
 * Created by user on 2018/1/15.
 */

public class BaseApplication extends Application {
    private static final String TAG = "BaseApplication";
    private static BaseApplication mApp;

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        /**
         * 初始化logger
         */
        initLogger();
        /**
         * 初始化x5
         */
        initX5WebView();
        /**
         * 初始化微信
         */
        WxManager.getInstance().regToWx();
        /**
         * 初始化bugly
         */
        Bugly.init(this, BuildConfig.buglyAppId, TextUtils.equals(BuildConfig.BUILD_TYPE, "debug"));
        /**
         * 初始化jpush
         */
        JPushInterface.setDebugMode(TextUtils.equals(BuildConfig.BUILD_TYPE, "debug"));
        JPushInterface.init(this);

        registerActivityLifecycleCallbacks(new SwitchBackgroundCallbacks());
    }

    private void initLogger() {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(true)  // (Optional) Whether to show thread info or not. Default true
//                .methodCount(0)         // (Optional) How many method line to show. Default 2
//                .methodOffset(7)        // (Optional) Hides internal method calls up to offset. Default 5
//                .logStrategy(customLog) // (Optional) Changes the log strategy to print out. Default LogCat
                .tag("StevenBase")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
    }

    public void initX5WebView() {
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.d(TAG, " onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {

            }
        };
        // 防止和crosswalk冲突、多进程加快初始化速度
        HashMap map = new HashMap();
        map.put(TbsCoreSettings.TBS_SETTINGS_USE_PRIVATE_CLASSLOADER, true);
        map.put(TbsCoreSettings.TBS_SETTINGS_USE_SPEEDY_CLASSLOADER, true);
        map.put(TbsCoreSettings.TBS_SETTINGS_USE_DEXLOADER_SERVICE, true);
        QbSdk.initTbsSettings(map);
        //x5内核初始化接口
        QbSdk.initX5Environment(BaseApplication.getAppContext(), cb);
    }

    /**
     * 获取application context
     *
     * @return
     */
    public static BaseApplication getAppContext() {
        return mApp;
    }

    private class SwitchBackgroundCallbacks implements Application.ActivityLifecycleCallbacks {

        @Override
        public void onActivityCreated(Activity activity, Bundle bundle) {
            ActivityManagerUtil.getInstance().pushOneActivity(activity);
        }

        @Override
        public void onActivityStarted(Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {

        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            ActivityManagerUtil.getInstance().popOneActivity(activity);
        }
    }
}

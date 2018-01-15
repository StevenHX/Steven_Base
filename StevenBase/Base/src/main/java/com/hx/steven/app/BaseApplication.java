package com.hx.steven.app;
import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.hx.steven.util.ActivityManagerUtil;
import com.hx.steven.util.CrashHandlerUtil;

/**
 * Created by user on 2018/1/15.
 */

public class BaseApplication extends Application{
    private static BaseApplication mApp;
    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        CrashHandlerUtil crashHandlerUtil = CrashHandlerUtil.getInstance();
        crashHandlerUtil.init(this);
        crashHandlerUtil.setCrashTip("很抱歉，程序出现异常，即将退出！");

        registerActivityLifecycleCallbacks(new SwitchBackgroundCallbacks());
    }

    /**
     * 获取application context
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

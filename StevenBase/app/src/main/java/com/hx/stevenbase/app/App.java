package com.hx.stevenbase.app;

import android.content.Context;

import com.facebook.stetho.Stetho;
import com.hx.steven.app.BaseApplication;

/**
 * Created by user on 2018/1/15.
 */

public class App extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);//初始化Stetho调试工具
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        /**
         * 有没有很模糊，其实他也说是分包的问题，这里我声明一下，的确是分包问题，首先在android5.0以上不牵扯MultiDex分包问题，但是在android4.4甚至以下版本就有这个，但是怎么解决呢？我们需要引入一个包：

         compile 'com.android.support:multidex:1.0.0'
         1
         引入这个来解决分包问题

         一. 从sdk\extras\android\support\multidex\library\libs 目录将android-support-multidex.jar导入工程中
         二. 如果你的工程中已经含有Application类,那么让它继承android.support.multidex.MultiDexApplication类,
         如果你的Application已经继承了其他类并且不想做改动，那么还有另外一种使用方式,覆写attachBaseContext()方法:
         public class MyApplication extends FooApplication {
         @Override
         protected void attachBaseContext(Context base) {
         super.attachBaseContext(base);
         MultiDex.install(this);
         }
         }
         然后这样的话就可以解决我们的项目在android4.4以下版本中报错找不到java.lang.NoClassDefFoundError: org.greenrobot.greendao.database.StandardDatabase
         */
    }
}

package com.hx.stevenbase.app;

import android.content.Context;

import com.hx.steven.app.BaseApplication;
import com.hx.stevenbase.gen.DaoMaster;
import com.hx.stevenbase.gen.DaoSession;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import org.greenrobot.greendao.database.Database;

/**
 * Created by user on 2018/1/15.
 */

public class App extends BaseApplication {
   private static DaoSession daoSession;
    private RefWatcher refWatcher;
    @Override
    public void onCreate() {
        super.onCreate();
        initDataBase(getAppContext());//配置数据库
        refWatcher = initLeakCanary();//配置内存泄漏工具
    }

    /**
     * 初始化LeakCanary
     */
    private RefWatcher initLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {//1
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return RefWatcher.DISABLED;
        }
        return LeakCanary.install(this);
    }
    public  static RefWatcher getRefWatcher(Context context) {
        App leakApplication = (App) context.getApplicationContext();
        return leakApplication.refWatcher;
    }

    /**
     * 初始化数据库
     */
    private void initDataBase(BaseApplication appContext) {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(appContext,"USER");
        Database db = devOpenHelper.getWritableDb();
        DaoMaster master = new DaoMaster(db);
        daoSession = master.newSession();
    }
    public  static DaoSession getDaoSession(){
        return daoSession;
    }
}

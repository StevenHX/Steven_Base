package com.hx.stevenbase.app;

import com.facebook.stetho.Stetho;
import com.hx.steven.app.BaseApplication;
import com.hx.stevenbase.gen.DaoMaster;
import com.hx.stevenbase.gen.DaoSession;

import org.greenrobot.greendao.database.Database;

/**
 * Created by user on 2018/1/15.
 */

public class App extends BaseApplication {
   private static DaoSession daoSession;
    @Override
    public void onCreate() {
        super.onCreate();
        initDataBase(getAppContext());//配置数据库
        Stetho.initializeWithDefaults(this);//初始化Stetho调试工具
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

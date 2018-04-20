package com.hx.steven.database;

import android.database.sqlite.SQLiteDatabase;

/**
 *
 * Created by HuangXiao on 2018/4/18.
 */

public abstract class BaseDBHelper {
    /**
     * 全局数据库锁对象
     */
    protected static final Object LOCK_OBJECT = new Object();

    protected BaseDBHelper() {
    }

    /**
     * 初始化
     */
    public void init() {
        synchronized (LOCK_OBJECT) {
            int[] versions = DBOpenHelper.getInstance().getVersions();
            SQLiteDatabase db = DBOpenHelper.getInstance().getWritableDatabase();
            if (versions[0] != versions[1]) {
                onUpdate(db, versions[1], versions[0]);
            }
            if (!db.isOpen()) { // onUpdate 可能被关闭
                db = DBOpenHelper.getInstance().getWritableDatabase();
            }
            onCreate(db);
        }
    }

    public SQLiteDatabase getWritableDatabase() {
        return DBOpenHelper.getInstance().getWritableDatabase();
    }

    public SQLiteDatabase getReadableDatabase() {
        return DBOpenHelper.getInstance().getReadableDatabase();
    }

    protected abstract void onCreate(SQLiteDatabase db);

    protected abstract void onUpdate(SQLiteDatabase db, int oldVersion, int newVersion);
}

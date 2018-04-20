package com.hx.steven.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 *
 * Created by huangxiao on 2018/4/18.
 */

public class DBOpenHelper extends SQLiteOpenHelper {
    //单例
    private static DBOpenHelper instance;

    /**
     * App版本 1.6.0 -> 数据库版本 1
     * App版本 1.6.1 -> 数据库版本 2
     */
    private static final int   VERSION  = 1; //数据库版本
    private int[] versions = new int[2];

    public DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public static DBOpenHelper getInstance() {
        return instance;
    }

    /**
     * 初始化Application调用
     *
     * @param context
     */
    public static void init(Context context,String name) {
        if (instance == null)
            synchronized (DBOpenHelper.class) {
                if (instance == null)
                    instance = new DBOpenHelper(context,name,null,VERSION);
            }
    }

    /**
     * 获取版本信息
     */
    public int[] getVersions() {
        return versions;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        versions[0] = newVersion;
        versions[1] = oldVersion;
    }
}

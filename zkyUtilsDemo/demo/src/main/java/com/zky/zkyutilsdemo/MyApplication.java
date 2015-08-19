package com.zky.zkyutilsdemo;

import android.database.sqlite.SQLiteDatabase;

import com.zky.zkyutils.BaseApplication;

import green.dao.htj.DaoMaster;
import green.dao.htj.DaoSession;
import green.dao.htj.upgrade.MyOpenHelper;

public class MyApplication extends BaseApplication {
    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    /*
     初始化数据库
     */
    @Override
    protected void setupDatabase() {
        MyOpenHelper helper = new MyOpenHelper(this, "HUANT_TAI_JI_DB", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}

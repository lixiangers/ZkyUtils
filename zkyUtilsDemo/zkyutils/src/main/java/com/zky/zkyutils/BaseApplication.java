package com.zky.zkyutils;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;

public abstract class BaseApplication extends Application {

    public static Context applicationContext = null;
    public static String url_domain;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = this.getApplicationContext();
        setupDatabase();
        Fresco.initialize(this); //初始化 fresco 图片加载框架
    }

    /**
     * 初始化数据库
     */
    protected abstract void setupDatabase();
}

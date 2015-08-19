package com.zky.zkyutils;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

public abstract class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        setupDatabase();
        Fresco.initialize(this);//初始化 fresco 图片加载框架
    }

    /**
     * 初始化数据库
     */
    protected abstract void setupDatabase();
}

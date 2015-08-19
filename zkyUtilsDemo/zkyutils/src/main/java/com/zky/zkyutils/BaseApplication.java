package com.zky.zkyutils;

import android.app.Application;

public abstract class BaseApplication extends Application {
    public static BaseApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        setupDatabase();
    }

    protected abstract void setupDatabase();
}

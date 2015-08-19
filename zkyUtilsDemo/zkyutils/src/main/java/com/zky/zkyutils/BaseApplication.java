package com.zky.zkyutils;

import android.app.Application;

public abstract class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        setupDatabase();
    }

    protected abstract void setupDatabase();
}

package com.zky.zkyutilsdemo;

import android.app.Application;

public class MyApplication extends Application {
    public static MyApplication instace;

    public MyApplication() {
        instace = this;
    }
}

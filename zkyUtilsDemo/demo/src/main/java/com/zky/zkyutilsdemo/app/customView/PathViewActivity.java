package com.zky.zkyutilsdemo.app.customView;

import android.app.Activity;
import android.os.Bundle;

public class PathViewActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MySurfaceView(getApplicationContext()));
//        setContentView(new DrawingWithBezier(getApplicationContext()));
    }
}

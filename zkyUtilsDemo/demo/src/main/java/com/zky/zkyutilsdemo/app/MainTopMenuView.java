package com.zky.zkyutilsdemo.app;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.zky.zkyutilsdemo.R;

public class MainTopMenuView extends LinearLayout {
    public MainTopMenuView(Context context) {
        this(context, null);
    }

    public MainTopMenuView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MainTopMenuView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.activity_main_top_menu,this);
    }
}

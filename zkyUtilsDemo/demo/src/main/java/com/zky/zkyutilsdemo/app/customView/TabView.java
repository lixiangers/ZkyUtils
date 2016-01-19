package com.zky.zkyutilsdemo.app.customView;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zky.zkyutilsdemo.R;

public class TabView extends LinearLayout {

    private TextView tvTitle;

    public TabView(Context context) {
        this(context, null);
    }

    public TabView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.view_tab, this);
        tvTitle = (TextView) findViewById(R.id.tv_tab_text);
    }

    public void setTitle(String title) {
        tvTitle.setText(title);
    }

}

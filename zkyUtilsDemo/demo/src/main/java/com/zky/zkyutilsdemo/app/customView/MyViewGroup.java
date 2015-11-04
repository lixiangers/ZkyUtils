package com.zky.zkyutilsdemo.app.customView;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.Scroller;

import com.zky.zkyutils.utils.LogUtils;

public class MyViewGroup extends LinearLayout {
    private boolean s1 = true;
    Scroller mScroller = null;

    public MyViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScroller = new Scroller(context);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void computeScroll() {
        LogUtils.d("testView","computeScroll");
        if (mScroller.computeScrollOffset()) {
            LogUtils.d("testView","getCurrX:"+mScroller.getCurrX());
            scrollTo(mScroller.getCurrX(), 0);
            postInvalidate();
        }
    }

    public void beginScroll() {
        if (!s1) {
            mScroller.startScroll(0, 0, 0, 0, 1000);
            s1 = true;
        } else {
            mScroller.startScroll(0, 0, -500, 0, 1000);
            s1 = false;
        }
        invalidate();
    }
}

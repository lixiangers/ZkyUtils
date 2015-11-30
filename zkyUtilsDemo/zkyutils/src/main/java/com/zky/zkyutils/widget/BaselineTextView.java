package com.zky.zkyutils.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

public class BaselineTextView extends TextView {

    public BaselineTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int yOffset = getHeight() - getBaseline();
        Paint.FontMetrics fontMetrics = getPaint().getFontMetrics();
        Log.d("fontMetrics", "topY     is:" + fontMetrics.top);
        Log.d("fontMetrics", "ascentY  is:" + fontMetrics.ascent);
        Log.d("fontMetrics", "descentY is:" + fontMetrics.descent);
        Log.d("fontMetrics", "bottomY  is:" + fontMetrics.bottom);
        Log.d("fontMetrics", "leading  is:" + fontMetrics.leading);
        Log.d("fontMetrics", "Baseline  is:" + getBaseline());
        Log.d("fontMetrics", "Height  is:" + getHeight());
        Log.d("fontMetrics", "yOffset  is:" + yOffset);
        canvas.translate(0, -yOffset / 2);//上移动，是上面没有空白
        super.onDraw(canvas);
    }
}
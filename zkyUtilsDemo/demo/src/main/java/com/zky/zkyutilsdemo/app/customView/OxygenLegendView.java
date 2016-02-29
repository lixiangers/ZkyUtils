package com.zky.zkyutilsdemo.app.customView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.zky.zkyutils.utils.DeviceUtils;
import com.zky.zkyutilsdemo.R;

public class OxygenLegendView extends View {

    public static final int MIN_NORMAL_VALUE = 94;
    public static final int MAX_OXYGEN_VALUE = 99;
    public static final int MIN_OXYGEN_VALUE = 70;
    private final Paint mTextPaint;
    private final int statusImageWidth;//状态图标宽度
    private final int statusImageHeight;//状态图标高度

    private final int legendHeight;//图例区域高度

    private final int colorNormal;
    private final int colorLow;
    private final int colorHigh;
    private final int legendHighWidth;
    private int legendLowWidth;
    private int legendNormalWidth;
    private int mAscent;

    private int percent = 97;//血氧百分比

    public OxygenLegendView(Context context) {
        this(context, null);
    }

    public OxygenLegendView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OxygenLegendView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);

        statusImageWidth = DeviceUtils.dip2px(getContext(), 46);
        statusImageHeight = DeviceUtils.dip2px(getContext(), 16);
        legendHeight = DeviceUtils.dip2px(getContext(), 7);

        colorLow = getResources().getColor(R.color.legendLow);
        colorNormal = getResources().getColor(R.color.legendNormal);
        colorHigh = getResources().getColor(R.color.legendHeigh);

        legendHighWidth = statusImageWidth / 2 + 5;//保证在最高的的时候绘制状态图标也不超过屏幕的最右边
    }

    /**
     * @see android.view.View#measure(int, int)
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec),
                measureHeight(heightMeasureSpec));
    }

    /**
     * Determines the width of this view
     *
     * @param measureSpec A measureSpec packed into an int
     * @return The width of the view, honoring constraints from measureSpec
     */
    private int measureWidth(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            // We were told how big to be
            result = specSize;
        } else {
            result = 100 + getPaddingLeft()
                    + getPaddingRight();//中间的刻度值最小是100px
            if (specMode == MeasureSpec.AT_MOST) {
                // Respect AT_MOST value if that was what is called for by measureSpec
                result = Math.min(result, specSize);
            }
        }

        return result;
    }

    /**
     * Determines the height of this view
     *
     * @param measureSpec A measureSpec packed into an int
     * @return The height of the view, honoring constraints from measureSpec
     */
    private int measureHeight(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            // We were told how big to be
            result = specSize;
        } else {
            result = DeviceUtils.dip2px(getContext(), 70) + getPaddingTop() + getPaddingBottom();
            if (specMode == MeasureSpec.AT_MOST) {
                // Respect AT_MOST value if that was what is called for by measureSpec
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    public void setOxygenValue(int value) {
        if (value < MIN_OXYGEN_VALUE)
            value = MIN_OXYGEN_VALUE;
        else if (value > MAX_OXYGEN_VALUE)
            value = MAX_OXYGEN_VALUE;

        percent = value;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        legendLowWidth = (getMeasuredWidth() - getPaddingLeft() - getPaddingRight() - legendHighWidth) * 11 / 16;//血氧低区域:血氧正常区域 11:5
        legendNormalWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight() - legendHighWidth - legendLowWidth;//血氧正常区域的宽度

        int textSize = DeviceUtils.dip2px(getContext(), 15);
        mTextPaint.setTextSize(textSize);
        mTextPaint.setColor(getResources().getColor(R.color.black_deep));
        mAscent = (int) mTextPaint.ascent();
        canvas.drawText("血氧图例", getPaddingLeft(), getPaddingTop() - mAscent, mTextPaint);

        int legendRectTop = getPaddingTop() + DeviceUtils.dip2px(getContext(), 40);

        drawLegendRect(canvas, legendRectTop);
        drawStatusImage(canvas, legendRectTop);
    }

    private void drawStatusImage(Canvas canvas, int legendRectTop) {
        int centerX = 0;
        Bitmap bitmap = null;
        String text = "";
        if (percent >= MIN_NORMAL_VALUE && percent <= MAX_OXYGEN_VALUE) {
            centerX = getPaddingLeft() + legendLowWidth + legendNormalWidth / (MAX_OXYGEN_VALUE - MIN_NORMAL_VALUE) * (percent - MIN_NORMAL_VALUE);//从94-99
            mTextPaint.setColor(colorNormal);
            bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_xyybz);
            text = "正常";
        } else if (percent >= MIN_OXYGEN_VALUE) {
            centerX = getPaddingLeft() + legendLowWidth / MIN_NORMAL_VALUE * percent; //从0-94
            mTextPaint.setColor(colorLow);
            bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_xyyby);
            text = "异常";
        }

        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setTextSize(DeviceUtils.dip2px(getContext(), 11));
        Rect textRect = new Rect(centerX - statusImageWidth / 2, legendRectTop - DeviceUtils.dip2px(getContext(), 5) - statusImageHeight,
                centerX + statusImageWidth / 2,
                legendRectTop - DeviceUtils.dip2px(getContext(), 5));

        canvas.drawBitmap(bitmap, textRect.left, textRect.top, mTextPaint);
        drawText(textRect, canvas, text);
    }

    private void drawLegendRect(Canvas canvas, int legendRectTop) {
        Rect lowRect = new Rect(getPaddingLeft(), legendRectTop, getPaddingLeft() + legendLowWidth, legendRectTop + legendHeight);
        mTextPaint.setColor(colorLow);
        mTextPaint.setStyle(Paint.Style.FILL);
        canvas.drawRect(lowRect, mTextPaint);

        Rect normalRect = new Rect(getPaddingLeft() + legendLowWidth, legendRectTop, getPaddingLeft() + legendLowWidth + legendNormalWidth, legendRectTop + legendHeight);
        mTextPaint.setColor(colorNormal);
        canvas.drawRect(normalRect, mTextPaint);

        Rect highRect = new Rect(getPaddingLeft() + legendLowWidth + legendNormalWidth, legendRectTop,
                getPaddingLeft() + legendLowWidth + legendNormalWidth + legendHighWidth, legendRectTop + legendHeight);
        mTextPaint.setColor(colorHigh);
        canvas.drawRect(highRect, mTextPaint);

        mTextPaint.setTextSize(DeviceUtils.dip2px(getContext(), 13));
        mTextPaint.setColor(getResources().getColor(R.color.black_deep));
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mAscent = (int) mTextPaint.ascent();
        canvas.drawText("94%", getPaddingLeft() + legendLowWidth, legendRectTop + legendHeight - mAscent + 10, mTextPaint);
        canvas.drawText("99%", getPaddingLeft() + legendLowWidth + legendNormalWidth, legendRectTop + legendHeight - mAscent + 10, mTextPaint);
    }

    private void drawText(Rect rect, Canvas canvas, String text) {
        mTextPaint.setTextSize(DeviceUtils.dip2px(getContext(), 10));
        mTextPaint.setColor(Color.WHITE);
        Paint.FontMetricsInt fontMetrics = mTextPaint.getFontMetricsInt();
        int baseline = (rect.bottom + rect.top - fontMetrics.bottom - fontMetrics.top) / 2;
        // 下面这行是实现水平居中，drawText对应改为传入targetRect.centerX()
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(text, rect.centerX(), baseline, mTextPaint);
    }
}

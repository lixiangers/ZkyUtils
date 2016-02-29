package com.zky.zkyutilsdemo.app.customView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.nineoldandroids.animation.ValueAnimator;
import com.zky.zkyutils.utils.DeviceUtils;
import com.zky.zkyutilsdemo.R;

public class OxygenValueView extends View implements ValueAnimator.AnimatorUpdateListener {
    private static final int FULL_ANGLE = 360;
    private final Paint mPaint;
    private final float mRingWidth;
    private float mStart = 90f; //起始角度。0度在3点钟方向
    private float delta;
    private ValueAnimator anim2;
    private int displayPercent;//显示的百分比（动画显示）
    private RectF mBigOval;

    public OxygenValueView(Context context) {
        this(context, null);
    }

    public OxygenValueView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OxygenValueView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mRingWidth = DeviceUtils.dip2px(getContext(), 9);
        mPaint = new Paint();
        mPaint.setAntiAlias(true); //消除锯齿

        // 创建ValueAnimator动画。ofFloat()的参数是从"动画开始" 到 "动画结束"对应的值。
        anim2 = ValueAnimator.ofInt(0, 0);
// 总的显示时间
        anim2.setDuration(1000);
// 变化模式
        anim2.setInterpolator(new LinearInterpolator());
// 监听：每次变化时的回调函数
        anim2.addUpdateListener(this);
    }

    private void setDelta(float delta) {
        this.delta = delta;
    }

    public void setPercent(int percent) {
//        ofFloat()的参数是从"动画开始" 到 "动画结束"对应的值。
        anim2.setIntValues(0, percent);
        anim2.start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        int sideLength = Math.min(widthSpecSize, heightSpecSize);
        setMeasuredDimension(sideLength, sideLength);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //先确定View的大小，这里我让View为一个正方形。取width和height都设置为Math.min(width, height)即可。
        //知道了View的大小，我们在这个正方形里面取一个小正方形放在正中间。两个正方形的距离就是弧形宽度的一半。然后Paint分别像内外各扩散1/2弧宽即可。
        //在Rect中画内切圆.因为我们的圆弧的宽度比较宽。画笔是已中心点分别向两边扩算宽度，所以这是区域为什么storeWidth/2的原因。
        mBigOval = new RectF(mRingWidth / 2 + getPaddingLeft(), mRingWidth / 2 + getPaddingTop(),
                getWidth() - (mRingWidth + 1) / 2 - getPaddingRight(), getHeight() - (mRingWidth + 1) / 2 - getPaddingBottom());

        mPaint.setStyle(Paint.Style.STROKE); //绘制空心圆
        mPaint.setStrokeWidth(mRingWidth); //设置进度条宽度
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND); //设置圆角
//        mPaint.setColor(getResources().getColor(R.color.gray_normal)); //设置底色颜色
//        canvas.drawArc(mBigOval, mStart, FULL_ANGLE, false, mPaint);//背景弧

        mPaint.setColor(getResources().getColor(R.color.legendNormal)); //设置进度条颜色
        canvas.drawArc(mBigOval, mStart, delta, false, mPaint);

        drawText(canvas, mPaint, mBigOval, displayPercent + "%");
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        Integer animatedValue = (Integer) animation.getAnimatedValue();
        setDelta(FULL_ANGLE * animatedValue / 100f);
        setDisplayPercent(animatedValue);
        invalidate();
    }

    private void setDisplayPercent(int displayPercent) {
        this.displayPercent = displayPercent;
    }

    private void drawText(Canvas canvas, Paint paint, RectF rect, String text) {
        paint.setStrokeWidth(1);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setTextSize(DeviceUtils.dip2px(getContext(), 25));
        paint.setColor(getResources().getColor(R.color.legendNormal));

        Paint.FontMetricsInt centerFontMetrics = paint.getFontMetricsInt();
//        FontMetrics.top的数值是个负数，其绝对值就是字体绘制边界到baseline的距离
        int centerFontHeight = centerFontMetrics.bottom - centerFontMetrics.top;
        int offY = centerFontHeight / 2 + centerFontMetrics.top; //字体高度的1/2 + baseline(负数)
        float baseline = rect.centerY() - offY;
        // 下面这行是实现水平居中，drawText对应改为传入targetRect.centerX()
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(text, rect.centerX(), baseline, paint);

        paint.setTextSize(DeviceUtils.dip2px(getContext(), 14));
        paint.setColor(getResources().getColor(R.color.gray_normal));
        Paint.FontMetricsInt newFontMetrics = paint.getFontMetricsInt();
        //center font 的base line - center font 的top- new font bottom
        canvas.drawText("血氧", rect.centerX(), baseline - (-centerFontMetrics.top) - newFontMetrics.bottom, paint);
        //center font 的base line + center font 的bottom + new font top
        canvas.drawText("正常", rect.centerX(), baseline + centerFontMetrics.bottom + (-newFontMetrics.top), paint);
    }
}

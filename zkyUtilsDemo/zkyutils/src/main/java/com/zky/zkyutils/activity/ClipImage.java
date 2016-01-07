package com.zky.zkyutils.activity;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author huyx
 * @desc 阴影部分view
 */
public class ClipImage extends View {


    public final static int SQUARE = 1;
    public final static int RECT_HORIZONAL = 2;
    public final static int RECT_VERTICAL = 3;
    int shape = 1;// 1剪裁方形图片 2剪裁文章签名封面(横向矩形) 3 剪裁活动封面 (竖直矩形)

    public ClipImage(Context context) {
        super(context);
    }

    public ClipImage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ClipImage(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = this.getWidth();
        int height = this.getHeight();
        Paint paint = new Paint();
        int offY1 = (height - width * 9 / 10) / 2;
        int offY2 = (height + width * 9 / 10) / 2;
        int offX1 = width / 20;
        int offX2 = width * 19 / 20;
        switch (shape) {
            case 1:
                offY1 = (height - width * 9 / 10) / 2;
                offY2 = (height + width * 9 / 10) / 2;
                break;
            case 2:
                offY1 = (height - width * 9 / 20) / 2;
                offY2 = (height + width * 9 / 20) / 2;
                break;
            case 3:
                offY1 = (height - width * 9 / 8) / 2;
                offY2 = (height + width * 9 / 8) / 2;  //3/4
                offX1 = width / 8;
                offX2 = width * 7 / 8;
                break;
        }

        //画周围背景
        paint.setColor(0xaa000000);
        canvas.drawRect(0, 0, width, offY1, paint);
        canvas.drawRect(0, offY1, offX1,
                offY2, paint);
        canvas.drawRect(offX2, offY1, width,
                offY2, paint);
        canvas.drawRect(0, offY2, width, height, paint);
        //画框
        paint.setColor(Color.WHITE);
        canvas.drawRect(offX1 - 1, offY1 - 1,
                offX2 + 1, offY1, paint);
        canvas.drawRect(offX1 - 1, offY1,
                offX1, offY2, paint);
        canvas.drawRect(offX2, offY1,
                offX2 + 1, offY2, paint);
        canvas.drawRect(offX1 - 1, offY2,
                offX2 + 1, offY2 + 1, paint);

    }


    public void setClipFrameShape(int shape) {
        this.shape = shape;
        invalidate();
    }


}

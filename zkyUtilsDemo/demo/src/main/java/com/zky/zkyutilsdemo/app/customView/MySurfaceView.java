package com.zky.zkyutilsdemo.app.customView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MySurfaceView extends SurfaceView {
    private final Timer mTimer;
    private final MyTimerTask mTimerTask;
    private final Paint mGesturePaint = new Paint();
    private final Path mPath = new Path();
    private final Rect mInvalidRect = new Rect();
    private Context mContex;
    private SurfaceHolder sfh;
    private Canvas canvas;
    private int distance = 20;//每个点直接的距离
    private int oldY;//上一个点的y
    private int mX;//向左偏移量
    private boolean isDrawing;
    private int i;

    public MySurfaceView(Context context) {
        super(context);
        mContex = context;
        sfh = this.getHolder();
        mGesturePaint.setAntiAlias(true);
        mGesturePaint.setStyle(Paint.Style.STROKE);
        mGesturePaint.setStrokeWidth(5);
        mGesturePaint.setColor(Color.WHITE);

        mTimer = new Timer();
        mTimerTask = new MyTimerTask();

        sfh.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                canvas = holder.lockCanvas();
                canvas.drawColor(Color.BLACK);
                holder.unlockCanvasAndPost(canvas);
                mTimer.schedule(mTimerTask, 0, 1000);
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                mTimer.cancel();
            }
        });
    }

    public void drawCanvas(int y) {
        try {
            canvas = sfh.lockCanvas(new Rect(0, 0, getWidth(), getHeight()));
//            canvas = sfh.lockCanvas(null);
            if (canvas != null) {
//                canvas.drawPath(mPath, mGesturePaint);
                canvas.save();
                mX -= distance;
                canvas.translate(mX, 0);
                canvas.drawLine(getWidth() - distance, oldY, getWidth(), y, mGesturePaint);
                canvas.restore();
                oldY = y;
            }
        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            if (canvas != null)
                sfh.unlockCanvasAndPost(canvas);
        }
    }

    class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            drawCanvas(new Random().nextInt(200));
            i++;
        }
    }
}

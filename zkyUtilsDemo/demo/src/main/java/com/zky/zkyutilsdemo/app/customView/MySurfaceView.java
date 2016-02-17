package com.zky.zkyutilsdemo.app.customView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ArrayBlockingQueue;

public class MySurfaceView extends SurfaceView {
    private final Timer mTimer;
    private final MyTimerTask mTimerTask;
    private final Paint mGesturePaint = new Paint();
    private int MAX_COUNT;
    private SurfaceHolder sfh;
    private Canvas canvas;
    private int distance = 20;//每个点直接的距离
    private ArrayBlockingQueue<Integer> pressureValueQueue;
    private Path path;
    private Integer[] data;

    public MySurfaceView(Context context) {
        super(context);
        sfh = this.getHolder();
        mGesturePaint.setAntiAlias(true);
        mGesturePaint.setStyle(Paint.Style.STROKE);
        mGesturePaint.setStrokeWidth(5);
        mGesturePaint.setColor(Color.WHITE);

        mTimer = new Timer();
        mTimerTask = new MyTimerTask();
        path = new Path();

        sfh.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                MAX_COUNT = getMeasuredWidth() / distance + 1;
                pressureValueQueue = new ArrayBlockingQueue<Integer>(MAX_COUNT);

                canvas = holder.lockCanvas();
                canvas.drawColor(Color.BLACK);
                holder.unlockCanvasAndPost(canvas);
                mTimer.schedule(mTimerTask, 2000, 1000);
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

    public void drawCanvas() {
        try {
            canvas = sfh.lockCanvas();
            path = getPath();
            canvas.drawColor(Color.BLACK);
            canvas.drawPath(path, mGesturePaint);

        } catch (Exception e) {
        } finally {
            if (canvas != null)
                sfh.unlockCanvasAndPost(canvas);
        }
    }

    private Path getPath() {
        path.reset();
        data = new Integer[pressureValueQueue.size()];
        data = pressureValueQueue.toArray(data);
        int size = data.length;
        if (size > 0) {
            path.moveTo(getMeasuredWidth() - (size - 0) * distance, data[0]);
//            Log.d("canvas:", "moveTo:" + "X:" + (getMeasuredWidth() - (size - 0) * distance) + " Y:" + array[0]);

            for (int i = 1; i < size; i++) {
//                preX = getMeasuredWidth() - (size - i + 1) * distance;
//                preY = array[i - 1];
                //                controlX = (toX - preX) / 2 + toX;
//                controlY = (toY - preY) / 2 + toY;
//                path.quadTo(controlX, controlY, toX, toY);
                path.lineTo(getMeasuredWidth() - (size - i) * distance, data[i]);
//                Log.d("canvas:", "lineTo:" + "startX:" + preX + " startY:" + preY + " toX:" + toX + " toY:" + toY + " controlX:" + controlX + " controlY:" + controlY);
            }
        }

        return path;
    }

    class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            if (pressureValueQueue.size() == MAX_COUNT)
                pressureValueQueue.poll();
            pressureValueQueue.offer(new Random().nextInt(500));
            drawCanvas();
        }

    }
}

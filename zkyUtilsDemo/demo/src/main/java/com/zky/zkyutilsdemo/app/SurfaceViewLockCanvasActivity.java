package com.zky.zkyutilsdemo.app;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.zky.zkyutilsdemo.R;

public class SurfaceViewLockCanvasActivity extends Activity {
    private SurfaceHolder holder;
    private Paint paint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surface_view);
        paint = new Paint();
        SurfaceView surface = (SurfaceView) findViewById(R.id.SurfaceView);
        //初始化SurfaceHolder对象
        holder = surface.getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                //锁定整个SurfaceView
                Canvas canvas = holder.lockCanvas();
                //绘制背景
                Bitmap back = BitmapFactory.decodeResource(getResources(), R.mipmap.title);
                //绘制背景
                canvas.drawBitmap(back, 0, 0, null);
                //绘制完成，释放画布，提交修改
                holder.unlockCanvasAndPost(canvas);
                //重新锁一次，“持久化”上次所绘制内容
                //本次lockCanvas会遮挡上次lockCanvas
                holder.lockCanvas(new Rect(0, 0, 0, 0));
                holder.unlockCanvasAndPost(canvas);
                holder.lockCanvas(new Rect(0, 0, 0, 0));
                holder.unlockCanvasAndPost(canvas);
                holder.lockCanvas(new Rect(0, 0, 0, 0));
                holder.unlockCanvasAndPost(canvas);
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width,
                                       int height) {
                // TODO Auto-generated method stub

            }
        });

        surface.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    int cx = (int) event.getX();
                    int cy = (int) event.getY();
                    //锁定SurfaceView的布局区域，只更新局部内容
                    Canvas canvas = holder.lockCanvas(new Rect(cx - 50, cy - 50, cx + 50, cy + 50));
                    //保存canvas当前状态
                    canvas.save();
                    //旋转画布
                    canvas.rotate(30, cx, cy);
                    paint.setColor(Color.RED);
                    //绘制红色方块
                    canvas.drawRect(cx - 40, cy - 40, cx, cy, paint);
                    //恢复canvas之前的保存状态
                    canvas.restore();
                    paint.setColor(Color.GREEN);
                    //绘制绿色方块
                    canvas.drawRect(cx, cy, cx + 40, cy + 40, paint);
                    //绘制完成，释放画布，提交修改
                    holder.unlockCanvasAndPost(canvas);
                }
                return false;
            }
        });
    }
}

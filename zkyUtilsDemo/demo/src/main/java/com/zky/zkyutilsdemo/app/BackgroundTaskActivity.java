package com.zky.zkyutilsdemo.app;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import com.lidroid.xutils.task.Priority;
import com.zky.zkyutils.utils.Constants;
import com.zky.zkyutils.utils.LogUtils;
import com.zky.zkyutils.utils.ToastUtils;
import com.zky.zkyutilsdemo.R;
import com.zky.zkyutilsdemo.backgroundTask.HandlerListener;
import com.zky.zkyutilsdemo.backgroundTask.NumberCalcHandler;

public class BackgroundTaskActivity extends ActionBarActivity {
    private NumberCalcHandler calcHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_background_task);

        findViewById(R.id.bt_begin_calc).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HandlerListener listener = new HandlerListener() {
                    @Override
                    public void onStart() {
                        ToastUtils.showText(getApplicationContext(), "On start");
                        LogUtils.d(Constants.TASK_TAG, "On start");

                    }

                    @Override
                    public void onCancel() {
                        ToastUtils.showText(getApplicationContext(), "On cancel");
                        LogUtils.d(Constants.TASK_TAG, "On cancel");

                    }

                    @Override
                    public void onProgress(int i) {
                        ToastUtils.showText(getApplicationContext(), "current ：" + i);
                        LogUtils.d(Constants.TASK_TAG, "current ：" + i);
                    }

                    @Override
                    public void onFinish(String result) {
                        ToastUtils.showText(getApplicationContext(), "finish ：" + result);
                        LogUtils.d(Constants.TASK_TAG, "finish ：" + result);
                    }
                };

                calcHandler = new NumberCalcHandler(listener);
                calcHandler.execute(1, 10, 1 * 1000);

                new NumberCalcHandler(listener).execute(11, 20, 2 * 1000);
                new NumberCalcHandler(listener).execute(21, 30, 2 * 1000);
                new NumberCalcHandler(listener).execute(31, 40, 2 * 1000);
                new NumberCalcHandler(listener).execute(41, 50, 2 * 1000);
                new NumberCalcHandler(listener).execute(51, 60, 2 * 1000);
                new NumberCalcHandler(listener).execute(61, 70, 2 * 1000);
                new NumberCalcHandler(listener).execute(71, 80, 2 * 1000);
                new NumberCalcHandler(listener).execute(81, 90, 2 * 1000);
                new NumberCalcHandler(listener).execute(91, 100, 2 * 1000);
                new NumberCalcHandler(listener).execute(101, 110, 2 * 1000);
                new NumberCalcHandler(listener).execute(111, 120, 2 * 1000);
                new NumberCalcHandler(listener).execute(121, 130, 2 * 1000);
                NumberCalcHandler priorityUpHandler = new NumberCalcHandler(listener);
                priorityUpHandler.setPriority(Priority.UI_TOP);
                priorityUpHandler.execute(131, 140, 2 * 1000);
            }
        });

        findViewById(R.id.bt_cancel_cala).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (calcHandler != null)
                    calcHandler.cancel();
            }
        });
    }
}

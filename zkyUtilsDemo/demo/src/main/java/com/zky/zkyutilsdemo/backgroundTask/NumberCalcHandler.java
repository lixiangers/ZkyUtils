package com.zky.zkyutilsdemo.backgroundTask;

import com.lidroid.xutils.task.PriorityAsyncTask;
import com.zky.zkyutils.utils.Constants;
import com.zky.zkyutils.utils.LogUtils;
import com.zky.zkyutils.utils.StringUtils;

public class NumberCalcHandler extends PriorityAsyncTask<Integer, Integer, String> {
    private HandlerListener listener;

    public NumberCalcHandler(HandlerListener listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(Integer... params) {
        LogUtils.d(Constants.TASK_TAG, "Thread Name:" + Thread.currentThread().getName());
        if (params.length != 3)
            return StringUtils.EMPTY;

        int start = params[0];
        int end = params[1];
        int intervalTime = params[2];
        long result = 0;
        for (int i = start; i < end; i++) {
            try {
                Thread.sleep(intervalTime);
                result += i;
                this.publishProgress(i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return "calc result:" + result;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (listener != null)
            listener.onStart();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (listener != null)
            listener.onFinish(s);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        if (listener != null)
            listener.onProgress(values[0]);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        if (listener != null)
            listener.onCancel();
    }
}

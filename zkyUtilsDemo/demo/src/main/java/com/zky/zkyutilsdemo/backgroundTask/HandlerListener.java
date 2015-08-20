package com.zky.zkyutilsdemo.backgroundTask;

public interface HandlerListener {
    void onStart();

    void onCancel(String result);

    void onProgress(int i);

    void onFinish(String result);

}

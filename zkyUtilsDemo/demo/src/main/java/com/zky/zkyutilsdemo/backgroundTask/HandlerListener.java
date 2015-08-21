package com.zky.zkyutilsdemo.backgroundTask;

public interface HandlerListener {
    void onStart();

    void onCancel();

    void onProgress(int i);

    void onFinish(String result);

}

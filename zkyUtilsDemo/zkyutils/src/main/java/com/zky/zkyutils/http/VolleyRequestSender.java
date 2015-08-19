package com.zky.zkyutils.http;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/*
  Volley queue 帮助类
 */
public class VolleyRequestSender {

    private volatile static VolleyRequestSender mInstance;
    private RequestQueue mQueue;

    private VolleyRequestSender(Context context) {
        mQueue = Volley.newRequestQueue(context);
    }

    public static VolleyRequestSender getInstance(Context context) {
        if (mInstance == null)
            synchronized (VolleyRequestSender.class) {
                if (mInstance == null) {
                    mInstance = new VolleyRequestSender(context);
                }
            }
        return mInstance;
    }

    public void send(Request request) {
        mQueue.add(request);
    }
}

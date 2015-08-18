package com.zky.zkyutils.http;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleryRequestSender {

    private volatile static VolleryRequestSender mInstance;
    private RequestQueue mQueue;

    private VolleryRequestSender(Context context) {
        mQueue = Volley.newRequestQueue(context);
    }

    public static VolleryRequestSender getInstance(Context context) {
        if (mInstance == null)
            synchronized (VolleryRequestSender.class) {
                if (mInstance == null) {
                    mInstance = new VolleryRequestSender(context);
                }
            }
        return mInstance;
    }

    public void send(Request request) {
        mQueue.add(request);
    }
}

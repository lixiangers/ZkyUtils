package com.zky.zkyutils.http;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import javax.net.ssl.SSLSocketFactory;

/*
  Volley queue 帮助类
 */
public class VolleyRequestSender {

    private volatile static VolleyRequestSender mInstance;
    private RequestQueue mQueue;

    private VolleyRequestSender(Context context, SSLSocketFactory sslSocketFactory) {
        mQueue = Volley.newRequestQueue(context, new OkHttpStack(sslSocketFactory));
    }

    /**
     * @param context
     * @param sslSocketFactory https ssl的认证类 如果不是https 传null
     * @return
     */
    public static VolleyRequestSender getInstance(Context context, SSLSocketFactory sslSocketFactory) {
        if (mInstance == null)
            synchronized (VolleyRequestSender.class) {
                if (mInstance == null) {
                    mInstance = new VolleyRequestSender(context, sslSocketFactory);
                }
            }
        return mInstance;
    }

    public void send(Request request) {
        mQueue.add(request);
    }
}

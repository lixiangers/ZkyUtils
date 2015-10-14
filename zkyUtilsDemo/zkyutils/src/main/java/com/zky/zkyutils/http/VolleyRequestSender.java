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
    private final Context context;
    private SSLSocketFactory sslSocketFactory;
    private RequestQueue mQueue;
    private RequestQueue mHttpsQueue;

    private VolleyRequestSender(Context context) {
        this.context = context;
        mQueue = Volley.newRequestQueue(context, new OkHttpStack());
    }

    /**
     * @param context
     * @return
     */
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

    public synchronized void sendHttps(Request request, SSLSocketFactory sslSocketFactory) {
        this.sslSocketFactory = sslSocketFactory;
        if (mHttpsQueue == null)
            mHttpsQueue = Volley.newRequestQueue(context, new OkHttpStack(this.sslSocketFactory));
        mHttpsQueue.add(request);
    }
}

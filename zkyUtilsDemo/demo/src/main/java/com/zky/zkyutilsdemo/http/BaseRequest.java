package com.zky.zkyutilsdemo.http;

import com.android.volley.Response;
import com.zky.zkyutils.http.GsonRequest;
import com.zky.zkyutils.http.IVolleyRequest;
import com.zky.zkyutils.utils.DeviceUtils;
import com.zky.zkyutilsdemo.MyApplication;

import java.lang.reflect.Type;

public abstract class BaseRequest<T> extends GsonRequest<T> {
    public BaseRequest(Response.Listener responseListener, Response.ErrorListener errorListener, Type type) {
        super(responseListener, errorListener, type);
    }


    @Override
    public boolean isConnectNetwork() {
        return DeviceUtils.isNetworkConnected(MyApplication.instace);
    }

    @Override
    public IVolleyRequest getVolleyRequest() {
        return new HttpRequestVolley(this);
    }
}

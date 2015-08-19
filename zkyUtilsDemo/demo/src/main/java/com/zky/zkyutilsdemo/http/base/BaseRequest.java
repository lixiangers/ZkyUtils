package com.zky.zkyutilsdemo.http.base;

import com.android.volley.Response;
import com.zky.zkyutils.http.GsonRequest;
import com.zky.zkyutils.http.IVolleyRequest;
import com.zky.zkyutils.utils.DeviceUtils;
import com.zky.zkyutilsdemo.MyApplication;

import java.lang.reflect.Type;

public abstract class BaseRequest<T> extends GsonRequest<T> implements IVolleyRequest {
    public BaseRequest(Response.Listener<T> responseListener, Response.ErrorListener errorListener, Type type) {
        super(responseListener, errorListener, type);
    }


    /*
    添加判断网络是否连接的方法，用于错误信息的判断
     */
    @Override
    public boolean isConnectNetwork() {
        return DeviceUtils.isNetworkConnected(MyApplication.instace);
    }

    /*
     http 请求的内容
     */
    @Override
    public IVolleyRequest getVolleyRequest() {
        return this;
    }
}

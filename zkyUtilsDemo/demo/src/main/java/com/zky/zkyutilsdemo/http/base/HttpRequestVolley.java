package com.zky.zkyutilsdemo.http.base;

import com.zky.zkyutils.http.IVolleyRequest;

/*
 request 的格式类，比如都有统一的字段，可以放在这个类中
 */
public class HttpRequestVolley<E extends BaseRequest> implements IVolleyRequest {

    protected E params;

    public HttpRequestVolley(E params) {
        this.params = params;
    }
}

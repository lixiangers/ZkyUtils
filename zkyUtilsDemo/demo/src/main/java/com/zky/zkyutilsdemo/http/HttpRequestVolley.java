package com.zky.zkyutilsdemo.http;

import com.zky.zkyutils.http.IVolleyRequest;


public class HttpRequestVolley<E extends BaseRequest> implements IVolleyRequest {

    protected E params;

    public HttpRequestVolley(E params) {
        this.params = params;
    }
}

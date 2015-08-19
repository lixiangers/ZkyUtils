package com.zky.zkyutilsdemo.http;

import com.android.volley.Response;
import com.google.gson.annotations.Expose;
import com.google.gson.reflect.TypeToken;
import com.zky.zkyutilsdemo.http.base.BaseRequest;
import com.zky.zkyutilsdemo.http.base.BaseResponse;

public class BindCIDRequest extends BaseRequest<Object> {
    @Expose
    public String token;

    @Expose
    public String client_id;


    public BindCIDRequest(Response.Listener<Object> responseListener, Response.ErrorListener errorListener) {
        super(responseListener, errorListener, new TypeToken<BaseResponse<Object>>() {
        }.getType());
    }

    @Override
    public String getApiUrl() {
        return "http://delivery.release.mrfood.cc:80/v1/login/report";
    }

    @Override
    public int getApiMethod() {
        return Method.POST;
    }
}

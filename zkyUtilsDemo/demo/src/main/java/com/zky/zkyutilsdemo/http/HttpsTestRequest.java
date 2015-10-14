package com.zky.zkyutilsdemo.http;

import com.android.volley.Response;
import com.google.gson.annotations.Expose;
import com.google.gson.reflect.TypeToken;
import com.zky.zkyutilsdemo.http.base.BaseRequest;
import com.zky.zkyutilsdemo.http.base.BaseResponse;

public class HttpsTestRequest extends BaseRequest<Object> {
    @Expose
    public String version;

    @Expose
    public String platform;


    public HttpsTestRequest(Response.Listener<Object> responseListener, Response.ErrorListener errorListener) {
        super(responseListener, errorListener, new TypeToken<BaseResponse<Object>>() {
        }.getType());
        setShouldCache(false);
    }

    @Override
    public String getApiUrl() {
        return "https://api.release.sangebaba.com/v2/version/update";
    }

    @Override
    public int getApiMethod() {
        return Method.GET;
    }


}

package com.zky.zkyutilsdemo.http;

import com.android.volley.Response;
import com.google.gson.reflect.TypeToken;

public class VersionRequest extends BaseRequest<CheckVersionResponse> {
    public VersionRequest(Response.Listener<CheckVersionResponse> responseListener, Response.ErrorListener errorListener) {
        super(responseListener, errorListener, new TypeToken<HttpResponseVolley<CheckVersionResponse>>() {
        }.getType());
    }

    @Override
    public String getApiUrl() {
        return "http://crm.release.mrfood.cc/open/app/checkUpdate?platform=1&type_id=1&version=\"1.0.0\"";
    }

    @Override
    public int getApiMethod() {
        return Method.GET;
    }
}

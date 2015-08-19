package com.zky.zkyutilsdemo.http;

import com.android.volley.Response;
import com.google.gson.reflect.TypeToken;
import com.zky.zkyutilsdemo.http.base.BaseRequest;
import com.zky.zkyutilsdemo.http.base.HttpResponseVolley;

public class VersionRequest extends BaseRequest<CheckVersionResponse> {
    public int platform = 1;
    public int type_id = 1;
    public String version;


    public VersionRequest(Response.Listener<CheckVersionResponse> responseListener, Response.ErrorListener errorListener) {
        super(responseListener, errorListener, new TypeToken<HttpResponseVolley<CheckVersionResponse>>() {
        }.getType());
    }

    @Override
    public String getApiUrl() {
        return "http://crm.release.mrfood.cc/open/app/checkUpdate?platform=" + platform + "&type_id=" + type_id + "&version=" + version;
    }

    @Override
    public int getApiMethod() {
        return Method.GET;
    }
}

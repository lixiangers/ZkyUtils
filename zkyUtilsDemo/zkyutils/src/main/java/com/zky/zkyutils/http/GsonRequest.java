package com.zky.zkyutils.http;

import android.os.Build;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.zky.zkyutils.utils.Constants;
import com.zky.zkyutils.utils.JsonUtils;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/*
  Json 请求的request 类
 */
public abstract class GsonRequest<T> extends Request<String> {

    private Response.Listener<T> mListener;

    private Type responseType;

    public GsonRequest(Response.Listener<T> responseListener, Response.ErrorListener errorListener, Type responseValueType) {
        super(errorListener);
        setRetryPolicy(new DefaultRetryPolicy(Constants.HTTP_RETRY_NUMBER, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        this.responseType = responseValueType;
        this.mListener = responseListener;
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        String jsonString;
        try {
            jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException e) {
            jsonString = new String(response.data);
        }

        if (response.statusCode == 200) {
            return Response.success(jsonString,
                    HttpHeaderParser.parseCacheHeaders(response));
        } else {
            return Response.error(new CustomVolleyError(response.statusCode, jsonString));
        }
    }

    @Override
    protected void deliverResponse(String response) {
        IVolleyResponse<T> httpResponse = null;
        try {
            httpResponse = JsonUtils.convertJsonToObject(response, responseType);
        } catch (Exception ex) {
            CustomVolleyError error = new CustomVolleyError(10001, "GSON_ABNORMAL" + ex.toString());
            deliverError(error);
            return;
        }

        if (httpResponse.noErrorMessage()) {
            if (this.mListener != null)
                this.mListener.onResponse(httpResponse.getResponseParams());
        } else {
            CustomVolleyError error = new CustomVolleyError(httpResponse.getStatus(), httpResponse.getError());
            deliverError(error);
        }
    }

    @Override
    public void deliverError(VolleyError error) {
        if (error instanceof CustomVolleyError)
            super.deliverError(error);

        else {
            CustomVolleyError customVolleyError = new CustomVolleyError(10002,
                    isConnectNetwork() ? "连接服务器失败" : "未连接网络"
            );
            super.deliverError(customVolleyError);
        }
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        byte[] bodyString = null;
        String json = JsonUtils.convertObjectToJson(getVolleyRequest());

        bodyString = json.getBytes();
        return bodyString;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> header = new HashMap<String, String>();
        header.put("Content-Type", "application/json");

        header.put("Cache-Control", "no-cache");
        header.put("Charsert", "UTF-8");
        if (Build.VERSION.SDK != null && Build.VERSION.SDK_INT > 13) {
            //HACK: fixed http://stackoverflow.com/questions/15411213/android-httpsurlconnection-eofexception
            header.put("Connection", "close");
        }
        return header;
    }

    @Override
    public int getMethod() {
        return getApiMethod();
    }

    @Override
    public String getUrl() {
        return getApiUrl();
    }

    @Override
    public RetryPolicy getRetryPolicy() {
        return super.getRetryPolicy();
    }


    public abstract String getApiUrl();

    public abstract int getApiMethod();

    public abstract boolean isConnectNetwork();

    public abstract IVolleyRequest getVolleyRequest();
}
package com.zky.zkyutilsdemo.http;

import com.zky.zkyutils.http.IVolleyResponse;

public class HttpResponseVolley<T> implements IVolleyResponse<T> {
    private int status;

    private String msg;

    private T value;


    public T getResponseParams() {
        return value;
    }

    public boolean noErrorMessage() {
        return status == 1;
    }

    public String getError() {
        return msg;
    }

    public int getStatus() {
        return status;
    }
}
package com.zky.zkyutilsdemo.http.base;

import com.google.gson.annotations.Expose;
import com.zky.zkyutils.http.IVolleyResponse;

/*
  http response 的格式类，一般的response有统一的格式
 */
public class BaseResponse<T> implements IVolleyResponse<T> {
    @Expose
    private int status;

    @Expose
    private String msg;

    @Expose
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
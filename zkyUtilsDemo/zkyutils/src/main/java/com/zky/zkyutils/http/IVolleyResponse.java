package com.zky.zkyutils.http;

public interface IVolleyResponse<T> {
    boolean noErrorMessage();

    T getResponseParams();

    int getStatus();

    String getError();
}

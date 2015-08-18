package com.zky.zkyutils.http;

import com.android.volley.VolleyError;

public class CustomVolleyError extends VolleyError {
    private int errorCode;
    private String errorMessage;

    public CustomVolleyError(int errorCode, String errorMessage) {
        super();
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public String getMessage() {
        return errorMessage;
    }


    public void setMessage(String message) {
        errorMessage = message;
    }
}

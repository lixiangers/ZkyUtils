package com.zky.zkyutils.http;

import com.android.volley.toolbox.HurlStack;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.OkUrlFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.SSLSocketFactory;

public class OkHttpStack extends HurlStack {
    private final OkUrlFactory mFactory;

    public OkHttpStack() {
        this(new OkHttpClient());
    }

    public OkHttpStack(OkHttpClient client) {
        this(client, null);
    }

    public OkHttpStack(SSLSocketFactory sslSocketFactory) {
        this(new OkHttpClient(), sslSocketFactory);
    }

    public OkHttpStack(OkHttpClient client, SSLSocketFactory sslSocketFactory) {
        //如果是https协议需要设置sslSocketFactory
        super(null, sslSocketFactory);
        if (client == null) {
            throw new NullPointerException("Client must not be null.");
        }
        mFactory = new OkUrlFactory(client);
    }

    @Override
    protected HttpURLConnection createConnection(URL url) throws IOException {
        return mFactory.open(url);
    }
}

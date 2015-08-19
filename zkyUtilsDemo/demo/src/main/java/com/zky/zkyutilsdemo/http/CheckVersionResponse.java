package com.zky.zkyutilsdemo.http;

import com.google.gson.annotations.Expose;

public class CheckVersionResponse {
    @Expose
    public int update;//是否需要加载1升级0不升级

    @Expose
    public String update_url;

    @Expose
    public int force_update;//是否强制升级 1 强制 0非强制

    @Expose
    public String title;

    @Expose
    public String info;

    @Expose
    public String version;
}

package com.zky.zkyutilsdemo.http;

public class CheckVersionResponse {
    public int update;//是否需要加载1升级0不升级
    public String update_url;
    public int force_update;//是否强制升级 1 强制 0非强制
    public String title;
    public String info;
    public String version;
}

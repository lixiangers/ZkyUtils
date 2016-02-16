package com.zky.zkyutilsdemo;

public class DemoDetails {
    public int titleId;
    public int descriptionId;
    public final Class<? extends android.app.Activity> activityClass;

    public DemoDetails(int titleId, int descriptionId,
                       Class<? extends android.app.Activity> activityClass) {
        super();
        this.titleId = titleId;
        this.descriptionId = descriptionId;
        this.activityClass = activityClass;
    }
}

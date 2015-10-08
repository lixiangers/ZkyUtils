package com.zky.zkyutils.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;

public interface MenuItemCallBack extends OnClickListener {
    public View Obtain(CustomMenuItem item, LayoutInflater layoutInflater);
    public void dismiss();
}

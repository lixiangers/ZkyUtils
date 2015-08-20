package com.zky.zkyutils.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtils {
    public static void showText(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }
}

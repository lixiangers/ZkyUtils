package com.zky.zkyutilsdemo.app;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.zky.zkyutilsdemo.R;

import static android.view.WindowManager.LayoutParams;

public class DialogTestActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_dialog);

        findViewById(R.id.bt_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
    }

    private void showDialog() {
        final Dialog dialog = new Dialog(DialogTestActivity.this, R.style.Dialog_Fullscreen);
        Window window = dialog.getWindow();
        // 设置宽高
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        // 设置布局
        window.setContentView(R.layout.alertdialog);

        //设置在底部显示
        window.setGravity(Gravity.BOTTOM);

        //设置宽度和屏幕一样
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = display.getWidth(); //设置宽度
        dialog.getWindow().setAttributes(lp);

        dialog.setCanceledOnTouchOutside(true);//点击外面消失
        // 设置监听
        Button ok = (Button) window.findViewById(R.id.btn_ok);
        Button cancel = (Button) window.findViewById(R.id.btn_cancel);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}

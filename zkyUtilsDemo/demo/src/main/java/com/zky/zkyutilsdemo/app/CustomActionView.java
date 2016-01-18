package com.zky.zkyutilsdemo.app;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.zky.zkyutils.utils.ToastUtils;
import com.zky.zkyutilsdemo.R;

public class CustomActionView extends RelativeLayout implements View.OnClickListener {
    public CustomActionView(Context context) {
        this(context, null);
    }

    public CustomActionView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomActionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.actionbar, this);

        findViewById(R.id.bt_left).setOnClickListener(this);
        findViewById(R.id.bt_right).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_left:
                ToastUtils.showText(getContext(), "left onClick");
                break;
            case R.id.bt_right:
                ToastUtils.showText(getContext(), "right onClick");
                break;
        }
    }
}

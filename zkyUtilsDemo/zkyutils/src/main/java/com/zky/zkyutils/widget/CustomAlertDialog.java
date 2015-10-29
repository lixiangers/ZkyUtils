package com.zky.zkyutils.widget;

import android.app.Activity;
import android.app.Dialog;
import android.os.Build;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.zky.zkyutils.R;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by dou on 2015/8/21.
 */
public class CustomAlertDialog extends Dialog {
    private final Activity context;
    private TextView titleView;
    protected Button positiveButton;
    protected Button negativeButton;
    private View closeView;
    private TextView messageTextView;
    private View splitView;

    public CustomAlertDialog(Activity context) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.context = context;
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        setContentView(R.layout.view_confirm_dialog);

        initUi();
        initListener();
    }

    @Override
    public void show() {
        if (context != null && !context.isFinishing())
            super.show();
    }

    private void initListener() {
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        closeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void initUi() {
        titleView = (TextView) findViewById(R.id.title);
        messageTextView = (TextView) findViewById(R.id.tv_message);
        positiveButton = (Button) findViewById(R.id.positive_button);
        negativeButton = (Button) findViewById(R.id.negative_button);
        splitView = findViewById(R.id.view_split);
        closeView = findViewById(R.id.view_close);
        closeView.setVisibility(GONE);
    }

    public void setMessage(String title) {
        messageTextView.setText(title);
    }

    public void setTitle(String title) {
        titleView.setVisibility(VISIBLE);
        titleView.setText(title);
    }

    public void setPositiveButton(String text, View.OnClickListener onClickListener) {
        positiveButton.setVisibility(VISIBLE);
        positiveButton.setText(text);
        positiveButton.setOnClickListener(onClickListener);
    }

    public void setNegativeButton(String text, View.OnClickListener onClickListener) {
        negativeButton.setVisibility(VISIBLE);
        negativeButton.setText(text);
        negativeButton.setOnClickListener(onClickListener);
    }

    public void displayWithStyle(DisplayStyle style) {
        negativeButton.setVisibility(style.negativeVisibility);
        positiveButton.setVisibility(style.positiveVisibility);
        splitView.setVisibility(style.splitViewVisibility);
        if (style.positiveBackgroundResourcesID > 0)
            positiveButton.setBackgroundResource(style.positiveBackgroundResourcesID);
        else {
            if (Build.VERSION.SDK_INT > 15)
                positiveButton.setBackground(null);
            else
                positiveButton.setBackgroundDrawable(null);
        }

        if (style.negativeBackgroundResourcesID > 0)
            negativeButton.setBackgroundResource(style.negativeBackgroundResourcesID);
        else {
            if (Build.VERSION.SDK_INT > 15) {
                negativeButton.setBackground(null);
            } else
                negativeButton.setBackgroundDrawable(null);
        }
    }

    public enum DisplayStyle {
        POSITIVE(VISIBLE, GONE, GONE, R.drawable.selector_bg_dialog_white, 0),
        NEGATIVE(GONE, VISIBLE, GONE, 0, R.drawable.selector_bg_dialog_white),
        POSITIVE_AND_NEGATIVE(VISIBLE, VISIBLE, VISIBLE, R.drawable.selector_bg_dialog_right_white, R.drawable.selector_bg_dialog_left_white);

        public int positiveVisibility = VISIBLE;
        public int negativeVisibility = VISIBLE;

        public int positiveBackgroundResourcesID;
        public int negativeBackgroundResourcesID;

        public int splitViewVisibility = VISIBLE;

        DisplayStyle(int positiveVisibility, int negativeVisibility, int splitViewVisibility, int positiveBackgroundResourcesId, int negativeBackgroundResourcesId) {
            this.positiveVisibility = positiveVisibility;
            this.negativeVisibility = negativeVisibility;
            this.splitViewVisibility = splitViewVisibility;
            this.positiveBackgroundResourcesID = positiveBackgroundResourcesId;
            this.negativeBackgroundResourcesID = negativeBackgroundResourcesId;
        }
    }
}

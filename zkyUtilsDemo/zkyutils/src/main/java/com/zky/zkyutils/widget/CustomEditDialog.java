package com.zky.zkyutils.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.text.InputFilter;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zky.zkyutils.R;
import com.zky.zkyutils.utils.StringUtils;

/**
 * Created by dou on 2015/8/21.
 */
public class CustomEditDialog extends Dialog {
    private int textLength = 0;
    private boolean isSingle = false;
    private Activity context;

    private TextView titleTextView;
    private EditText contentEditView;
    private TextView tipTextView;
    private Button negativeButton;
    private ImageView imageView;
    private Button positiveButton;

    private String title;
    private String content;

    private String tips;
    private String value;
    private int imageViewId;

    public CustomEditDialog(Activity context) {
        this(context, R.style.Dialog);
    }

    public CustomEditDialog(Activity context, int theme) {
        super(context, theme);
        this.context = context;
        setContentView(R.layout.dialog_edit);
        initUi();
    }

    private void initUi() {
        titleTextView = (TextView) findViewById(R.id.warning_title);
        contentEditView = (EditText) findViewById(R.id.waring_content);
        imageView = (ImageView) findViewById(R.id.iv_image);
        contentEditView.setSingleLine(isSingle);
        if (textLength != 0) {
            contentEditView.setFilters(new InputFilter[]{new InputFilter.LengthFilter(textLength)});
        }
        positiveButton = (Button) findViewById(R.id.btn_close);

        tipTextView = (TextView) findViewById(R.id.tv_tip);
        negativeButton = (Button) findViewById(R.id.btn_quit);

        if (StringUtils.isNotBlank(tips)) {
            tipTextView.setText(tips);
            tipTextView.setVisibility(View.VISIBLE);
        } else
            tipTextView.setVisibility(View.GONE);

        if (imageViewId > 0) {
            imageView.setImageResource(imageViewId);
            imageView.setVisibility(View.VISIBLE);
        } else
            imageView.setVisibility(View.GONE);

        if (title != null)
            titleTextView.setText(title);

        contentEditView.setHint(content);

        if (value != null)
            contentEditView.setText(value);


        contentEditView.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    InputMethodManager imm = (InputMethodManager) contentEditView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(contentEditView, 0);
                }
            }
        });
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String hint, String defaultValue) {
        this.content = hint;
        this.value = defaultValue;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public void setImageViewResourcesId(int resourcesId) {
        this.imageViewId = resourcesId;
    }

    public String getValue() {
        return contentEditView.getText().toString();
    }

    @Override
    public void show() {
        if (context != null && !context.isFinishing())
            super.show();
        contentEditView.requestFocus();
    }

    public void setPositiveButton(String text, View.OnClickListener onClickListener) {
        positiveButton.setText(text);
        positiveButton.setOnClickListener(onClickListener);
    }

    public void setNegativeButton(String text, View.OnClickListener onClickListener) {
        negativeButton.setText(text);
        negativeButton.setOnClickListener(onClickListener);
    }
}

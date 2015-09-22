package com.zky.zkyutils.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
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
public class EditDialog extends Dialog {
    private int textLength = 0;
    private boolean isSingle = false;
    Activity context;

    TextView title;

    EditText content;

    Button btn_close;

    String _title;

    String _content;

    String value;

    TextView tip;

    Button cancle;

    public android.view.View.OnClickListener listener;
    private String tips;
    private ImageView imageView;
    private int imageViewId;

    public EditDialog(Activity context) {
        super(context, R.style.Dialog);
        this.context = context;
    }

    public EditDialog(Activity context, boolean isSingle, int textLength) {
        super(context, R.style.Dialog);
        this.context = context;
        this.isSingle = isSingle;
        this.textLength = textLength;
    }

    public EditDialog(Activity context, int theme) {
        super(context, theme);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_edit);

        title = (TextView) findViewById(R.id.warning_title);
        content = (EditText) findViewById(R.id.waring_content);
        imageView = (ImageView) findViewById(R.id.iv_image);
        content.setSingleLine(isSingle);
        if (textLength != 0) {
            content.setFilters(new InputFilter[]{new InputFilter.LengthFilter(textLength)});
        }
        btn_close = (Button) findViewById(R.id.btn_close);

        tip = (TextView) findViewById(R.id.tv_tip);
        cancle = (Button) findViewById(R.id.btn_quit);

        if (StringUtils.isNotBlank(tips)) {
            tip.setText(tips);
            tip.setVisibility(View.VISIBLE);
        } else
            tip.setVisibility(View.GONE);

        if (imageViewId > 0) {
            imageView.setImageResource(imageViewId);
            imageView.setVisibility(View.VISIBLE);
        } else
            imageView.setVisibility(View.GONE);

        if (_title != null)
            title.setText(_title);

        content.setHint(_content);

        if (value != null)
            content.setText(value);

        btn_close.setOnClickListener(listener);

        cancle.setOnClickListener(listener);

        content.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    InputMethodManager imm = (InputMethodManager) content.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(content, 0);
                }
            }
        });
    }

    public void setBothListener(android.view.View.OnClickListener listener) {
        this.listener = listener;
    }

    public void setTitle(String title) {
        this._title = title;
    }

    public void setContent(String hint, String defaultValue) {
        this._content = hint;
        this.value = defaultValue;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public void setImageViewResourcesId(int resourcesId) {
        this.imageViewId = resourcesId;
    }

    public String getValue() {
        return content.getText().toString();
    }

    @Override
    public void show() {
        if (context != null && !context.isFinishing())
            super.show();
        content.requestFocus();
    }
}

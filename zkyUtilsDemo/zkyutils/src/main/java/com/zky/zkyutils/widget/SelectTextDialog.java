package com.zky.zkyutils.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.zky.zkyutils.R;

/**
 * Created by dou on 2015/8/21.
 */
public class SelectTextDialog  extends Dialog {
    Context context;

    TextView title;

    ListView content;

    String _title;

    String action[] = {"ÎÞÐ§¹û", "±ù¶³", "µ÷ÁÁ", "ºÚ°×", "¸¡µñ", "Ä¾¿Ì", "ÓÍ»­"};

    AdapterView.OnItemClickListener onItemClickListener;

    public SelectTextDialog(Context context) {
        super(context, R.style.Dialog);
        this.context = context;
    }

    public SelectTextDialog(Context context, int theme) {
        super(context, theme);
        this.context = context;
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_text_select);
        title = (TextView) findViewById(R.id.warning_title);
        content = (ListView) findViewById(R.id.waring_content);

        content.setAdapter(adapter);
        content.setOnItemClickListener(onItemClickListener);

        if (_title != null)
            title.setText(_title);
    }

    BaseAdapter adapter = new BaseAdapter() {

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = new TextView(parent.getContext());
                TextView text = (TextView) convertView;
                text.setPadding(15, 10, 15, 10);
                text.setTextColor(0xff000000);
                text.setTextSize(18);
            }
            TextView text = (TextView) convertView;
            text.setText(action[position]);

            return convertView;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public Object getItem(int position) {
            return action[position];
        }

        @Override
        public int getCount() {
            return action.length;
        }
    };

    public AdapterView.OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public String[] getAction() {
        return action;
    }

    public void setAction(String[] action) {
        this.action = action;
    }

    public void setTitle(String title) {
        this._title = title;
    }
}

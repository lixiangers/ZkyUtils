package com.zky.zkyutilsdemo.http;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.zky.zkyutils.widget.PickerView;
import com.zky.zkyutilsdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dou on 2015/8/21.
 */
public class PickerViewTestActivity extends Activity {



        private PickerView pickerView;
        private TextView textView, howToSet;
        private Button button;
        private int targetCount;
        private int targetNum = 4;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.picker_view_activity);

            initView();
        }

        private void initView() {

            pickerView = (PickerView) findViewById(R.id.sport_target_num);
            textView = (TextView) findViewById(R.id.target_count);

            List<String> data = new ArrayList<String>();
            int j = 0;
            for (int i = 1; i < 15; i++) {
                data.add("" + i * 1000);

                if (targetCount == i * 1000) {
                    targetNum = j;
                }
                j++;
            }
            for (int i = 0; i <= 7; i++) {
                data.add("" + (15000 + (i * 5000)));

                if (targetCount == (15000 + (i * 5000))) {
                    targetNum = j;
                }
                j++;
            }
            pickerView.setData(data);
            pickerView.setSelected(targetNum);//ÉèÖÃ³õÊ¼Öµ
            pickerView.setOnSelectListener(new PickerView.onSelectListener() {
                @Override
                public void onSelect(String text) {
                    textView.setText(text);
                    targetCount = Integer.valueOf(text);
                }
            });


        }

}

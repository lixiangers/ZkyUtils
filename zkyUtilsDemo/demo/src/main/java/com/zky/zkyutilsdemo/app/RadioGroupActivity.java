package com.zky.zkyutilsdemo.app;

import android.app.Activity;
import android.os.Bundle;

import com.zky.zkyutils.utils.ToastUtils;
import com.zky.zkyutils.view.CustomRadioGroup;
import com.zky.zkyutilsdemo.R;

public class RadioGroupActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio_group);

        CustomRadioGroup radioGroup = (CustomRadioGroup) findViewById(R.id.crg_test);
        radioGroup.setOnCheckedChangeListener(new CustomRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CustomRadioGroup group, int checkedId) {
                switch (checkedId)
                {
                    case R.id.food_class_et_2:
                        ToastUtils.showText(getApplicationContext(),"早餐");
                        break;
                    case R.id.food_class_et_5:
                        ToastUtils.showText(getApplicationContext(),"晚餐");
                        break;
                }
            }
        });
    }
}

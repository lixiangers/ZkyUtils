package com.zky.zkyutilsdemo.app.customView;

import android.app.Activity;
import android.os.Bundle;

import com.zky.zkyutilsdemo.R;

public class ArcViewActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arc);
        OxygenValueView view = (OxygenValueView) findViewById(R.id.oxygen_value_view);
        view.setPercent(94);
    }
}

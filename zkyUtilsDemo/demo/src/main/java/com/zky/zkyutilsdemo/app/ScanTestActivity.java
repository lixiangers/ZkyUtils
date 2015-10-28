package com.zky.zkyutilsdemo.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.zky.zkyutils.utils.Constant;
import com.zky.zkyutils.utils.StringUtils;
import com.zky.zkyutils.utils.ToastUtils;
import com.zxing.activity.CaptureActivity;
import com.zxing.camera.CameraManager;
import com.zky.zkyutilsdemo.R;

public class ScanTestActivity extends Activity implements View.OnClickListener {
    private static final int REQUEST_CODE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_scan);

        findViewById(R.id.bt_test_bar_code).setOnClickListener(this);
        findViewById(R.id.bt_test_qr_code).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_test_bar_code:
                Intent intent = new Intent(getApplicationContext(), CaptureActivity.class);
                intent.putExtra(CaptureActivity.EXTRA_TYPE, CameraManager.TYPE_BAR_CODE);
                startActivityForResult(intent, REQUEST_CODE_CAPTURE);
                break;
            case R.id.bt_test_qr_code:
                Intent intent2 = new Intent(getApplicationContext(), CaptureActivity.class);
                intent2.putExtra(CaptureActivity.EXTRA_TYPE, CameraManager.TYPE_QR_CODE);
                startActivityForResult(intent2, REQUEST_CODE_CAPTURE);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CAPTURE && resultCode == Activity.RESULT_OK) {
            String orderNumber = data.getStringExtra(Constant.MESSAGE);
            if (StringUtils.isBlank(orderNumber))
                return;
            ToastUtils.showText(getApplicationContext(),orderNumber);
        }
    }
}

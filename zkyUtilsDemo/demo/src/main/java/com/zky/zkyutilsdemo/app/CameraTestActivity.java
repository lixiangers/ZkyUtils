package com.zky.zkyutilsdemo.app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.zky.zkyutils.activity.SelectPicNewActivity;
import com.zky.zkyutils.utils.StringUtils;
import com.zky.zkyutilsdemo.R;

public class CameraTestActivity extends Activity {

    public static final int TO_SELECT_PHOTO = 1;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_test);

        findViewById(R.id.bt_pick).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), SelectPicNewActivity.class);
                startActivityForResult(intent1, TO_SELECT_PHOTO);
            }
        });

        imageView = (ImageView) findViewById(R.id.iv_image);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK)
            return;

        if (requestCode == TO_SELECT_PHOTO) {
            String path = data.getStringExtra(SelectPicNewActivity.EXTRA_IMAGE_PATH);
            if (StringUtils.isNotBlank(path)) {
                imageView.setImageBitmap(BitmapFactory.decodeFile(path));
            }
        }

    }
}

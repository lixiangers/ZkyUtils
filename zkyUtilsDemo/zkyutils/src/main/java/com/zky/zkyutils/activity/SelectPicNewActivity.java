package com.zky.zkyutils.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.zky.zkyutils.R;
import com.zky.zkyutils.utils.FileUtils;
import com.zky.zkyutils.utils.ToastUtils;

import org.joda.time.DateTime;

public class SelectPicNewActivity extends Activity implements OnClickListener {
    // 使用照相机拍照获取图片
    public static final int SELECT_PIC_BY_TACK_PHOTO = 1006;
    // 使用相册中的图片
    public static final int SELECT_PIC_BY_PICK_PHOTO = 1007;
    // 使用裁剪后的图片
    public static final int IMAGE_CUT = 1008;
    public static final String EXTRA_IMAGE_PATH = "image_path";
    private static final String TAG = "SelectPicActivity";
    // 判断图片是否需要裁剪
    boolean isNeedCut = true;
    private LinearLayout dialogLayout;
    private Button takePhotoBtn, pickPhotoBtn, cancelBtn;
    private String picPath;
    private Uri photoUri;
    private Intent lastIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_pic_layout);

        dialogLayout = (LinearLayout) findViewById(R.id.dialog_layout);
        dialogLayout.setOnClickListener(this);
        takePhotoBtn = (Button) findViewById(R.id.btn_take_photo);
        takePhotoBtn.setOnClickListener(this);
        pickPhotoBtn = (Button) findViewById(R.id.btn_pick_photo);
        pickPhotoBtn.setOnClickListener(this);
        cancelBtn = (Button) findViewById(R.id.btn_cancel);
        cancelBtn.setOnClickListener(this);
        lastIntent = getIntent();
        isNeedCut = lastIntent.getBooleanExtra("isNeedCut", true);
    }

    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.dialog_layout) {
            finish();
        } else if (i == R.id.btn_take_photo) {
            takePhoto();
        } else if (i == R.id.btn_pick_photo) {
            pickPhoto();
        } else {
            finish();
        }
    }

    private void takePhoto() {
        // Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        picPath = getSignatureImageName();
        // File photo = new File(picPath);
        // photoUri = Uri.fromFile(photo);
        // intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
        // intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
        Intent intent = new Intent(SelectPicNewActivity.this, CameraActivity.class);
        intent.putExtra("picPath", picPath);
        startActivityForResult(intent, SELECT_PIC_BY_TACK_PHOTO);
    }

    private String getSignatureImageName() {
        return FileUtils.getFileDirPath(getApplicationContext(), "image") + "/" + DateTime.now().toString("yyyyMMddHHmmss") + ".jpg";
    }

    private void pickPhoto() {
        Log.i(TAG, "pickPhoto");
        Intent intent;

        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");

        } else {
            intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        }

        startActivityForResult(intent, SELECT_PIC_BY_PICK_PHOTO);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        finish();
        return super.onTouchEvent(event);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("picPath", picPath);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        picPath = savedInstanceState.getString("picPath");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_CANCELED) {
            finish();
            return;
        }

        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case SELECT_PIC_BY_TACK_PHOTO:
                    doPhoto(picPath);
                    break;
                case SELECT_PIC_BY_PICK_PHOTO:
                    String imagePath = getPhotoPath(data);
                    Log.d("test image", "imagePath:" + imagePath);
                    if (imagePath == null || imagePath == "")
                        return;
                    doPhoto(imagePath);
                    break;
                case IMAGE_CUT:
                    try {
                        picPath = data.getStringExtra("picPath");
                        lastIntent.putExtra(EXTRA_IMAGE_PATH, picPath);
                        setResult(RESULT_OK, lastIntent);
                        finish();
                        Log.i(TAG, "IMAGE_CUT 进来Dfinish");
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.i(TAG, "IMAGE_CUT Exception =" + e.toString());
                    }
                    break;

            }
        }
    }

    private String getPhotoPath(Intent data) {
        String imagePath = "";
        if (data == null) {
            ToastUtils.showText(getApplicationContext(), getString(R.string.slect_image_error));
            return imagePath;
        }
        photoUri = data.getData();
        if (photoUri == null) {
            ToastUtils.showText(getApplicationContext(), getString(R.string.slect_image_error));
            return imagePath;
        }
        String[] pojo = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(photoUri,
                pojo,//查哪一列
                null, null, null);
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            if (cursor.getCount() > 0 && cursor.moveToFirst()) {
                imagePath = cursor.getString(column_index);
            }

            cursor.close();
            cursor = null;
        }


        return imagePath;
    }

    private void doPhoto(String picPath) {
        if (picPath != null) {
            Log.i(TAG, "SELECT_PIC_BY_TACK_PHOTO E");
//            int digree = SelectPicActivity.getDigree(picPath);
//            if (digree != 0) {
//                Log.i(TAG, "SELECT_PIC_BY_TACK_PHOTO F");
//                int width = getWindowManager().getDefaultDisplay().getWidth();
//                int height = getWindowManager().getDefaultDisplay().getHeight();
//                Bitmap bitmap = SelectPicActivity.compressImageByScale(picPath, width, height);
//                Bitmap bit = SelectPicActivity.RotatePic(digree, bitmap);
//                picPath = SelectPicActivity.saveMyBitmap("rotate.jpg", bit).getAbsolutePath();
//                bitmap.recycle();
//                bit.recycle();
//                System.gc();
//
//            }

            if (isNeedCut) {
                Log.i(TAG, "处理照片 剪切照片");
                // Intent intent = getCropImageIntent(Uri.fromFile(new File(
                // picPath)));
                // Intent intent = getCropImageIntent(photoUri);

                Intent intent = new Intent(SelectPicNewActivity.this, ClipPictureActivity.class);
                intent.putExtra("picPath", picPath);
                // intent.setData(photoUri);
                startActivityForResult(intent, IMAGE_CUT);
                return;
            }
            Intent data1 = new Intent();
            data1.putExtra(EXTRA_IMAGE_PATH, picPath);
            setResult(RESULT_OK, data1);
            finish();

        } else {
            Toast.makeText(this, R.string.choose_image_file_error, Toast.LENGTH_LONG).show();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

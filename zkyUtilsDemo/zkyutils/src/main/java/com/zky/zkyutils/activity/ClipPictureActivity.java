package com.zky.zkyutils.activity;

//javaapk.com提供测试

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.zky.zkyutils.R;
import com.zky.zkyutils.utils.ImageUtils;
import com.zky.zkyutils.utils.ToastUtils;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * @ͼƬք؅̵
 * @ӆ֯bޘͼ
 */
public class ClipPictureActivity extends Activity implements OnTouchListener,
        OnClickListener {
    // We can be in one of these 3 states
    static final int NONE = 0;
    static final int DRAG = 1;
    static final int ZOOM = 2;
    private static final String TAG = "ClipPictureActivity";
    ImageView srcPic;
    View sure, btn_back;
    ClipImage clipview;
    // These matrices will be used to move and zoom image
    Matrix matrix = new Matrix();
    Matrix savedMatrix = new Matrix();
    int mode = NONE;

    // Remember some things for zooming
    PointF start = new PointF();
    PointF mid = new PointF();
    double oldDist = 1f;
    int shape = 1;
    boolean isFirstInvalidate = true;
    int statusBarHeight = 0;
    int titleBarHeight = 0;
    private View view;
    private Runnable clipBitmap = new Runnable() {

        @Override
        public void run() {

            // final Bitmap fianBitmap =
            // CommonUtil.compressImageByQuality(getBitmap(), 100);
            Bitmap fianBitmap = getBitmap();

            Bitmap bit = ImageUtils.compressImageByQuality(fianBitmap, 100);
            final File f = ImageUtils.saveMyBitmap(getApplicationContext(), "cutPic_compressed" + System.currentTimeMillis() + ".jpg", bit);

            fianBitmap.recycle();
            bit.recycle();

            btn_back.post(new Runnable() {

                @Override
                public void run() {
                    if (f != null && f.exists()) {
                        Intent intent = new Intent();
                        intent.putExtra("picPath", f.getAbsolutePath());
                        setResult(RESULT_OK, intent);
                        ClipPictureActivity.this.finish();
                    } else {
                        ToastUtils.showText(getApplicationContext(), "ͼƬޘȡʧќ");
                    }
                }
            });

        }
    };

    @SuppressLint("NewApi")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clip_picture);
        clipview = (ClipImage) this.findViewById(R.id.clipPicturePage_clipView);
        srcPic = (ImageView) findViewById(R.id.clipPicturePage_PicToClip);
        sure = (TextView) findViewById(R.id.clipPicturePage_OK);
        btn_back = findViewById(R.id.clipPicturePage_back);

        srcPic.setOnTouchListener(this);
        sure.setOnClickListener(this);
        btn_back.setOnClickListener(this);

        Intent intent = getIntent();
//		Uri uri = getIntent().getData();
//		srcPic.setImageURI(uri);

        shape = intent.getIntExtra("shape", ClipImage.SQUARE);
        clipview.setClipFrameShape(shape);

        String picPath = intent.getStringExtra("picPath");
        if (picPath != null) {
            int width = getWindowManager().getDefaultDisplay().getWidth();
            int height = getWindowManager().getDefaultDisplay().getHeight();
            srcPic.setImageBitmap(ImageUtils.compressImageByScale(picPath, width, height));
        }

        ViewTreeObserver viewTreeObserver = clipview.getViewTreeObserver();
        viewTreeObserver
                .addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

                    @Override
                    public void onGlobalLayout() {
                        Log.i(TAG, "isFirstInvalidate=" + isFirstInvalidate);
                        if (isFirstInvalidate) {
                            isFirstInvalidate = false;
                            return;
                        }
                        int width = clipview.getWidth();
                        int height = clipview.getHeight();
                        float picWidth = srcPic.getDrawable().getBounds()
                                .width();
                        Log.i(TAG, "height=" + height);
                        Log.i(TAG, "picWidth=" + picWidth);
                        // float din=2*picWidth/height;
                        float din = width * 9 / 10 / picWidth;
                        Log.i(TAG, "din=" + din);
                        matrix.setScale(din, din);
                        float picright = srcPic.getDrawable().getBounds().right * din;
                        float bottom = srcPic.getDrawable().getBounds().bottom * din;

                        switch (shape) {
                            case ClipImage.SQUARE:
                                matrix.postTranslate(width * 19 / 20 - picright, (height - bottom) / 2);
                                break;
                            case ClipImage.RECT_HORIZONAL:
                                matrix.postTranslate(width * 19 / 20 - picright, (height - bottom) / 2);
                                break;
                            case ClipImage.RECT_VERTICAL:

                                din = width * 3 / 4 / picWidth;
                                Log.i(TAG, "din=" + din);
                                matrix.setScale(din, din);
                                picright = srcPic.getDrawable().getBounds().right * din;
                                bottom = srcPic.getDrawable().getBounds().bottom * din;

                                matrix.postTranslate(width * 7 / 8 - picright, (height - bottom) / 2);
                                break;
                        }

                        srcPic.setImageMatrix(matrix);

                    }
                });

    }

    /* ֢oʵЖנ֣ԥľ؅ճ̵Сìۍե֣ӆ֯ͼƬք٦Ŝìӎ߼Û̳քպë */
    public boolean onTouch(View v, MotionEvent event) {
        ImageView view = (ImageView) v;
        // Handle touch events here...
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                savedMatrix.set(matrix);
                // Տ׃Եʼ��׃
                start.set(event.getX(), event.getY());
                // Log.d(TAG, "mode=DRAG");
                mode = DRAG;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                oldDist = spacing(event);
                // Log.d(TAG, "oldDist=" + oldDist);
                if (oldDist > 10f) {
                    savedMatrix.set(matrix);
                    midPoint(mid, event);
                    mode = ZOOM;
                    // Log.d(TAG, "mode=ZOOM");
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                mode = NONE;
                // Log.d(TAG, "mode=NONE");
                break;
            case MotionEvent.ACTION_MOVE:
                if (mode == DRAG) {
                    // ...
                    matrix.set(savedMatrix);
                    matrix.postTranslate(event.getX() - start.x, event.getY()
                            - start.y);
                } else if (mode == ZOOM) {
                    double newDist = spacing(event);
                    // Log.d(TAG, "newDist=" + newDist);
                    if (newDist > 10f) {
                        matrix.set(savedMatrix);
                        double scale = newDist / oldDist;
                        matrix.postScale((float) scale, (float) scale, mid.x, mid.y);
                    }
                }
                break;
        }

        view.setImageMatrix(matrix);
        return true; // indicate event was handled
    }

    /**
     * Determine the space between the first two fingers
     */
    private double spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return Math.sqrt(x * x + y * y);
    }

    /**
     * Calculate the mid point of the first two fingers
     */
    private void midPoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }

    /* ֣ܷ޸ɫԤ@ */
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.clipPicturePage_OK) {
            new Thread(clipBitmap).start();

        } else if (i == R.id.clipPicturePage_back) {
            finish();

        }

    }

    /* ܱȡߘюȸԲŚքޘͼ */
    private Bitmap getBitmap() {
        getBarHeight();
        Bitmap screenShoot = takeScreenShot();

        int width = clipview.getWidth();
        int height = clipview.getHeight();
        Bitmap finalBitmap = null;
        switch (shape) {
            case ClipImage.SQUARE:
                finalBitmap = Bitmap.createBitmap(screenShoot,
                        width / 20, (height - width * 9 / 10) / 2 + titleBarHeight
                                + statusBarHeight, width * 9 / 10, width * 9 / 10);
                break;
            case ClipImage.RECT_HORIZONAL:
                finalBitmap = Bitmap.createBitmap(screenShoot,
                        width / 20, (height - width * 9 / 20) / 2 + titleBarHeight
                                + statusBarHeight, width * 9 / 10, width * 9 / 20);
                break;
            case ClipImage.RECT_VERTICAL:
                finalBitmap = Bitmap.createBitmap(screenShoot, width / 8,
                        (height - width * 9 / 8) / 2 + titleBarHeight + statusBarHeight,
                        width * 3 / 4, width * 9 / 8);
                break;
        }

        return finalBitmap;
    }

    private void getBarHeight() {
        // ܱȡ״̬8ٟ׈
        Rect frame = new Rect();
        this.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        statusBarHeight = frame.top;

        int contenttop = this.getWindow()
                .findViewById(Window.ID_ANDROID_CONTENT).getTop();
        int clipPageTitleBarHeight = findViewById(
                R.id.clipPicturePage_clipTitleBar).getHeight();
        // statusBarHeightˇʏĦ̹ȳք״̬8քٟ׈
        titleBarHeight = contenttop - statusBarHeight + clipPageTitleBarHeight;

        Log.v(TAG, "statusBarHeight = " + statusBarHeight
                + ", titleBarHeight = " + titleBarHeight + ", contenttop = "
                + contenttop);
    }

    // ܱȡActivityքޘǁ
    private Bitmap takeScreenShot() {
        view = this.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        return view.getDrawingCache();
    }

    private Bitmap decodeUriAsBitmap(Uri uri) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(getContentResolver()
                    .openInputStream(uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (view != null) {
            view.setDrawingCacheEnabled(false);
            view.destroyDrawingCache();
        }

    }
}
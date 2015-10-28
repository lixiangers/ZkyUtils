package com.zxing.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.text.TextUtils;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.zky.zkyutils.R;
import com.zky.zkyutils.utils.Constant;
import com.zky.zkyutils.utils.DebugLog;
import com.zxing.camera.CameraManager;
import com.zxing.decoding.CaptureActivityHandler;
import com.zxing.decoding.InactivityTimer;
import com.zxing.view.ViewfinderView;

import java.io.IOException;
import java.util.Vector;


public class CaptureActivity extends Activity implements Callback {

    public static final String EXTRA_IS_SCAN_PRINTER = "is_scan_printer_code";
    private CaptureActivityHandler handler;
    private ViewfinderView viewfinderView;
    private boolean hasSurface;
    private Vector<BarcodeFormat> decodeFormats;
    private String characterSet;
    private InactivityTimer inactivityTimer;
    private MediaPlayer mediaPlayer;
    private boolean playBeep;
    private static final float BEEP_VOLUME = 0.10f;
    private boolean vibrate;
    public static final String EXTRA_TYPE = "type";
    private int type = CameraManager.TYPE_QR_CODE;
    private TextView titleMid;
    private Button titleLeft;
    private ImageView flashSwtichBt;
    private TextView flashSwtichHint;
    private boolean isFlashLightOn = false;
    private ImageView hintIcon;
    private TextView hintText;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ViewUtil.addTopView(getApplicationContext(), this,
        // R.string.scan_card);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_capture_layout);
        if (getIntent() != null) {
            type = getIntent().getIntExtra(EXTRA_TYPE, -1) == -1 ? type : getIntent().getIntExtra(EXTRA_TYPE, -1);
        }
        CameraManager.init(getApplication());
        CameraManager.get().initType(type);
        viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
        hasSurface = false;
        inactivityTimer = new InactivityTimer(this);
        titleMid = (TextView) findViewById(R.id.title_mid_textview);
        titleLeft = (Button) findViewById(R.id.title_left_button);
        flashSwtichBt = (ImageView) findViewById(R.id.flashlight_switch);
        flashSwtichHint = (TextView) findViewById(R.id.flashlight_switch_hint);
        hintIcon = (ImageView) findViewById(R.id.hint_icon);
        hintText = (TextView) findViewById(R.id.hint_text);
        titleLeft.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (type == CameraManager.TYPE_QR_CODE) {
            hintIcon.setVisibility(View.GONE);
            hintText.setText(R.string.please_input_qr_code);
        } else {
            hintIcon.setBackgroundResource(R.drawable.scan_hint_code_icon);
            hintText.setText(R.string.please_input_bar_code);
        }
        flashSwtichBt.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                isFlashLightOn = !isFlashLightOn;
                if (isFlashLightOn) {
                    try {
                        CameraManager.get().openLight();
                    } catch (Exception e) {
                        // TODO: handle exception
                    }

                    flashSwtichBt.setBackgroundResource(R.drawable.falshlight_off);
                    flashSwtichHint.setText(R.string.close_flash_light);
                } else {
                    try {
                        CameraManager.get().closeLight();
                    } catch (Exception e) {

                        // TODO: handle exception
                    }

                    flashSwtichBt.setBackgroundResource(R.drawable.falshlight_on);
                    flashSwtichHint.setText(R.string.open_flash_light);
                }
            }
        });
    }

    SurfaceView surfaceView;

    @Override
    protected void onResume() {
        super.onResume();
        surfaceView = (SurfaceView) findViewById(R.id.preview_view);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        decodeFormats = null;
        characterSet = null;

        playBeep = true;
        AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            playBeep = false;
        }
        initBeepSound();
        vibrate = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        CameraManager.get().closeDriver();
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        super.onDestroy();
    }

    /**
     * Handler scan result
     *
     * @param result
     * @param barcode
     */
    public void handleDecode(Result result, Bitmap barcode) {
        inactivityTimer.onActivity();
        playBeepSoundAndVibrate();
        String resultString = result.getText();
        // FIXME
        if (TextUtils.isEmpty(resultString)) {
            Toast.makeText(CaptureActivity.this, "Scan failed!", Toast.LENGTH_SHORT).show();
            Intent resultIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putString(Constant.MESSAGE, resultString);
            resultIntent.putExtras(bundle);
            this.setResult(RESULT_OK, resultIntent);
        } else {
            // System.out.println("Result:"+resultString);
            // ToastUtil.showToast(resultString, Toast.LENGTH_SHORT);
            Intent resultIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putString(Constant.MESSAGE, resultString);
            resultIntent.putExtras(bundle);
            this.setResult(RESULT_OK, resultIntent);
        }
        CaptureActivity.this.finish();
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            CameraManager.get().openDriver(surfaceHolder);
            Point resolution = CameraManager.get().getCameraResolution();
            if (resolution != null) {
                LayoutParams params = surfaceView.getLayoutParams();
                int w = surfaceView.getWidth();
                int h = surfaceView.getHeight();

                float radio = Math.min((float) w / resolution.y, (float) h / resolution.x);
                params.width = (int) (resolution.y * radio);
                params.height = (int) (resolution.x * radio);
                surfaceView.requestLayout();
//				DebugLog.v("point", "w:"+w +" h:"+h);
//				DebugLog.v("point", "x:"+resolution.x +" y:"+resolution.y);
            }


//			LayoutParams params = surfaceView.getLayoutParams();
//			params.width = 320;
//			params.height = 480;
//			surfaceView.requestLayout();

        } catch (IOException ioe) {
            return;
        } catch (RuntimeException e) {
            return;
        }
        if (handler == null) {
            handler = new CaptureActivityHandler(this, decodeFormats, characterSet);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        DebugLog.v("surface", "===width:" + width + " height:" + height);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;

    }

    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    public Handler getHandler() {
        return handler;
    }

    public void drawViewfinder() {
        viewfinderView.drawViewfinder();

    }

    private void initBeepSound() {
        if (playBeep && mediaPlayer == null) {
            // The volume on STREAM_SYSTEM is not adjustable, and users found it
            // too loud,
            // so we now play on the music stream.
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(beepListener);

            AssetFileDescriptor file = getResources().openRawResourceFd(R.raw.beep);
            try {
                mediaPlayer.setDataSource(file.getFileDescriptor(), file.getStartOffset(), file.getLength());
                file.close();
                mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
                mediaPlayer.prepare();
            } catch (IOException e) {
                mediaPlayer = null;
            }
        }
    }

    private static final long VIBRATE_DURATION = 200L;

    private void playBeepSoundAndVibrate() {
        if (playBeep && mediaPlayer != null) {
            mediaPlayer.start();
        }
        if (vibrate) {
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }

    /**
     * When the beep has finished playing, rewind to queue up another one.
     */
    private final OnCompletionListener beepListener = new OnCompletionListener() {
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };

}
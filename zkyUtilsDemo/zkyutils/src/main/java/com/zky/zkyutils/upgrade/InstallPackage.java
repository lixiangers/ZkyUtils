package com.zky.zkyutils.upgrade;

import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.zky.zkyutils.R;
import com.zky.zkyutils.utils.FileUtils;
import com.zky.zkyutils.utils.StringUtils;
import com.zky.zkyutils.utils.ToastUtils;

import java.io.File;
import java.io.IOException;

public class InstallPackage {

    /**
     * 界面环境
     */
    Context context;

    /**
     * 下载地址
     */
    String url;

    String errorMessage = "服务器繁忙，请稍后再试";

    String downloadMessage = "正在下载新版本客户端……";

    Handler handler;

    public InstallPackage(Context context) {
        this.context = context;
    }

    public void install(boolean force_update, String url, String title, String message, String install_button, String cancel_button) {

        if (null == url || StringUtils.isBlank(url)) {
            ToastUtils.showText(context, R.string.down_url_null);
        } else {
            this.url = url;
        }
        if (null == message)
            message = "是否立即安装？";
        if (null == title)
            title = "安装提示";
        if (null == install_button)
            install_button = "立即安装";
        if (null == cancel_button)
            cancel_button = "稍后安装";

        Builder update = new Builder(context);
        update.setTitle(title);
        update.setMessage(message);
        update.setPositiveButton(install_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                downloadAndInstall();
            }
        });
        if (!force_update) {
            update.setNegativeButton(cancel_button, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        } else {
            update.setCancelable(false);
        }
        update.show();

    }

    public void downloadAndInstall() {
        final ProgressDialog pd = new ProgressDialog(context);
        handler = new Handler();

        pd.setMessage(downloadMessage);
        pd.show();
        pd.setCancelable(false);

        try {

            File tempDir = new File(FileUtils.getFileDirPath(context, "download"));
            if (!tempDir.exists()) {
                tempDir.mkdirs();
            }

            File temp = new File(FileUtils.getFileDirPath(context, "download"), "temp.apk");
            if (temp.exists())
                temp.delete();

            final String path = temp.getAbsolutePath();
            HttpUtils http = new HttpUtils();

            http.download(url.trim(), path, true, true, new RequestCallBack<File>() {

                @Override
                public void onLoading(long total, long current, boolean isUploading) {
                    pd.setMessage(downloadMessage + "\n" + StringUtils.getVolume(current) + " / " + StringUtils.getVolume(total));
                    super.onLoading(total, current, isUploading);
                }

                @Override
                public void onSuccess(ResponseInfo<File> result) {
                    pd.setCancelable(true);
                    pd.cancel();

                    if (result.result == null) {
                        ToastUtils.showText(context, errorMessage);
                    } else {
                        install(path) ;
                    }
                }

                @Override
                public void onFailure(HttpException error, String msg) {
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 有可以安装的文件，则直接安装
     *
     * @param path 可安装文件的路径，注意前面不带“file://”
     */
    public void install(String path) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse("file://" + path), "application/vnd.android.package-archive");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

}

package com.zky.zkyutils.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;

public class FileUtils {

    /**
     * 获取缓存目录路径
     *
     * @param context
     * @param dirName 目录名称
     * @return
     */
    public static File getDiskCacheDirFile(Context context, String dirName) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getAbsolutePath();
        } else {
            cachePath = context.getCacheDir().getAbsolutePath();
        }
        return new File(cachePath + File.separator + dirName);
    }

    public static String getFileDirPath(Context context, String dirName) {
        String filePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            filePath = context.getExternalFilesDir(dirName).getAbsolutePath();
        } else {
            filePath = context.getFilesDir().getAbsolutePath();
            filePath += (File.separator + dirName);
        }
        return filePath;
    }

    public void deleteFile(String path) {
        File file = new File(path);
        if (file.exists())
            file.delete();
    }
}

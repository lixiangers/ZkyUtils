package com.zky.zkyutils.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageUtils {
    /**
     * 按比例大小压缩方法（根据路径获取图片并压缩）：
     *
     * @return
     */
    public static Bitmap compressImageByScale(String srcPath, int reqWidth, int reqHeight) {
        final BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        newOpts.inPreferredConfig = Bitmap.Config.RGB_565;


        BitmapFactory.decodeFile(srcPath, newOpts);// 此时返回bm为空

        newOpts.inSampleSize = calculateInSampleSize1(newOpts, reqWidth, reqHeight);// 设置缩放比例
        newOpts.inJustDecodeBounds = false;
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        // return BitmapFactory.decodeFile(srcPath, newOpts);
        FileInputStream is = null;
        try {
            is = new FileInputStream(new File(srcPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return BitmapFactory.decodeStream(is, null, newOpts);
    }

    /**
     * 计算 inSampleSize 方法一
     *
     * @return
     */

    public static int calculateInSampleSize1(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and
            // keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    /**
     * 质量压缩法：*
     *
     * @param image
     * @return
     */

    public static Bitmap compressImageByQuality(Bitmap image, int picSize) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        if (!image.compress(Bitmap.CompressFormat.JPEG, 20, baos)) {// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
            int options = 100;
            while (baos.toByteArray().length / 1024 > picSize) { // 循环判断如果压缩后图片是否大于
                // picSize
                // kb,大于继续压缩
                baos.reset();// 重置baos即清空baos
                options -= 10;// 每次都减少10
                if (options == 30) { // 为保证图片不失真，压缩率最低不低于30%
                    break;
                }
                image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
            }
        }
        // image.compress(Bitmap.CompressFormat.JPEG, 30, baos);//
        // 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中

        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = false;
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//
        // 把压缩后的数据baos存放到ByteArrayInputStream中
        // byte[] data = baos.toByteArray();
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);//
        // 把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    /**
     * 质将bitmap存为文件
     *
     * @return
     */

    public static File saveMyBitmap(Context context, String filename, Bitmap bit) {

        File outFile = new File(FileUtils.getFileDirPath(context, "image") + "Cache/img_cache/");
        if (!outFile.exists()) {
            outFile.mkdirs();
        }

        File f = new File(outFile.getAbsolutePath() + File.separator + filename);
        try {
            f.createNewFile();
            FileOutputStream fOut = null;
            fOut = new FileOutputStream(f);
            if (bit != null) {
                bit.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
            }
            fOut.flush();
            fOut.close();

        } catch (IOException e1) {
            // TODO Auto-generated catch block
            f = null;
            e1.printStackTrace();
        }

        return f;

    }
}

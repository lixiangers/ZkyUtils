package com.zky.zkyutils.image;

import android.content.Context;
import android.net.Uri;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

public class ImageDisplayUtils {
    public static void display(Context context, SimpleDraweeView simpleDraweeView, String netPath) {
        display(context, simpleDraweeView, netPath, 0, 0);
    }

    public static void display(Context context, SimpleDraweeView simpleDraweeView, String netPath, float ratio) {
        display(context, simpleDraweeView, netPath, 0, ratio);
    }

    /**
     * 正常显示网络图片
     *
     * @param context
     * @param simpleDraweeView
     * @param netPath               图片url
     * @param placeholderDrawableId 占位图像资源
     * @param ratio                 图片显示比例  <=0 无效
     */
    public static void display(Context context, SimpleDraweeView simpleDraweeView, String netPath, int placeholderDrawableId, float ratio) {
        GenericDraweeHierarchyBuilder builder =
                new GenericDraweeHierarchyBuilder(context.getResources());

        if (placeholderDrawableId > 0) {
            builder.setPlaceholderImage(context.getResources().getDrawable(placeholderDrawableId));
        }

        GenericDraweeHierarchy hierarchy = builder.build();

        Uri uri = Uri.parse(netPath);
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .build();

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(simpleDraweeView.getController())
                .build();

        if (ratio > 0)
            simpleDraweeView.setAspectRatio(ratio);

        simpleDraweeView.setHierarchy(hierarchy);
        simpleDraweeView.setController(controller);
    }

    public static void displayRound(Context context, SimpleDraweeView simpleDraweeView, String netPath, float radius) {
        displayRound(context, simpleDraweeView, netPath, 0, radius);
    }

    /**
     * 显示圆角网络图片
     *
     * @param context
     * @param simpleDraweeView
     * @param netPath               图片url
     * @param placeholderDrawableId 占位图像资源
     * @param radius                四个角的弧度
     */
    public static void displayRound(Context context, SimpleDraweeView simpleDraweeView, String netPath, int placeholderDrawableId, float radius) {
        RoundingParams roundingParams = RoundingParams.fromCornersRadius(radius);
//        roundingParams.setBorder(R.color.transparent, 1.0f);
//        roundingParams.setOverlayColor(DoctorApplication.getInstance().getResources().getColor(R.color.my_white));

        GenericDraweeHierarchyBuilder builder =
                new GenericDraweeHierarchyBuilder(context.getResources());

        if (placeholderDrawableId > 0) {
            builder.setPlaceholderImage(context.getResources().getDrawable(placeholderDrawableId));
        }

        GenericDraweeHierarchy hierarchy = builder
                .setRoundingParams(roundingParams).build();

        Uri uri = Uri.parse(netPath);
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .build();

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(simpleDraweeView.getController())
                .build();


        simpleDraweeView.setHierarchy(hierarchy);
        simpleDraweeView.setController(controller);
    }
}

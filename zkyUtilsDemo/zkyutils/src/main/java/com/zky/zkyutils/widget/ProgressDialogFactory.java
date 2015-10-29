package com.zky.zkyutils.widget;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;

import com.zky.zkyutils.R;

/**
 * Created by dou on 2015/8/21.
 */
public class ProgressDialogFactory {
    private static Dialog mDialog = null;

    private static Dialog createRequestDialog(final Context context, String tip) {
        Dialog dialog = initDialog(context);

        TextView titleText = (TextView) dialog.findViewById(R.id.tvLoad);
        if (tip == null || tip.length() == 0) {
            titleText.setText(R.string.is_processing);
        } else {
            titleText.setText(tip);
        }
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    private static Dialog initDialog(Context context) {
        Dialog dialog = new Dialog(context, R.style.dialog);
        dialog.setContentView(R.layout.dialog_layout);
        return dialog;
    }

    public static void showRequestDialog(Context context, String content) {
        showRequestDialog(context, content, true);
    }

    public static void showRequestDialog(Context context, String content, boolean canCancel) {
        hideRequestDialog();
        mDialog = createRequestDialog(context, content);
        mDialog.setCancelable(canCancel);
        try {
            mDialog.show();
        } catch (Exception ex) {
            mDialog = null;
        }
    }

    public static void hideRequestDialog() {
        if (mDialog != null) {
            try {
                mDialog.dismiss();
            } catch (Exception ex) {
            } finally {
                mDialog = null;
            }
        }
    }
}

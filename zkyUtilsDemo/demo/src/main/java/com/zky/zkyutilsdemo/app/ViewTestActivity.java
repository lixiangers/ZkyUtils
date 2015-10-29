package com.zky.zkyutilsdemo.app;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.zky.zkyutils.utils.DeviceUtils;
import com.zky.zkyutils.utils.ToastUtils;
import com.zky.zkyutils.view.CustomBottomMenu;
import com.zky.zkyutils.view.CustomMenuItem;
import com.zky.zkyutils.view.CustomPopupMenu;
import com.zky.zkyutils.view.MenuItemCallBack;
import com.zky.zkyutils.widget.CustomAlertDialog;
import com.zky.zkyutils.widget.CustomEditDialog;
import com.zky.zkyutils.widget.PickerView;
import com.zky.zkyutils.widget.ProgressDialogFactory;
import com.zky.zkyutilsdemo.R;

import java.util.ArrayList;
import java.util.List;

import static android.view.WindowManager.LayoutParams;

public class ViewTestActivity extends Activity implements MenuItemCallBack {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_view);

        findViewById(R.id.bt_test_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        findViewById(R.id.bt_test_popup_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomPopupMenu titlePopup = new CustomPopupMenu(ViewTestActivity.this, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                titlePopup.addAction(new CustomMenuItem("设备详情", R.drawable.ic_detail));
                titlePopup.addAction(new CustomMenuItem("成员管理", R.drawable.ic_create_group));
                titlePopup.show(v);
                titlePopup.setItemOnClickListener(new CustomPopupMenu.OnItemOnClickListener() {
                    @Override
                    public void onItemClick(CustomMenuItem item, int position) {
                        ToastUtils.showText(getApplicationContext(), item.getTitle());
                    }
                });
            }
        });

        findViewById(R.id.bt_test_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomBottomMenu menu = new CustomBottomMenu(ViewTestActivity.this, ViewTestActivity.this);
                List<CustomMenuItem> itemList = new ArrayList<CustomMenuItem>();
                itemList.add(new CustomMenuItem("菜单1", 1));
                itemList.add(new CustomMenuItem("菜单2", 2));
                itemList.add(new CustomMenuItem("取消", 3));
                menu.setMenuList(itemList);
                menu.show();
            }
        });

        findViewById(R.id.bt_test_dialog_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplication(), DialogActivity.class));
            }
        });

        findViewById(R.id.bt_test_radio_group).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplication(), RadioGroupActivity.class));
            }
        });

        findViewById(R.id.bt_test_alertDialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CustomAlertDialog alertDialog = new CustomAlertDialog(ViewTestActivity.this);
                alertDialog.displayWithStyle(CustomAlertDialog.DisplayStyle.POSITIVE_AND_NEGATIVE);
                alertDialog.setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                        ToastUtils.showText(getApplicationContext(), "取消");
                    }
                });

                alertDialog.setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                        ToastUtils.showText(getApplicationContext(), "确定");
                    }
                });

                alertDialog.show();
            }
        });

        findViewById(R.id.bt_test_EditDialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CustomEditDialog editDialog = new CustomEditDialog(ViewTestActivity.this);
                editDialog.setTitle("dfd");
                editDialog.setContent("content", "value");
                editDialog.setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editDialog.dismiss();
                        ToastUtils.showText(getApplicationContext(), "取消");
                    }
                });

                editDialog.setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtils.showText(getApplicationContext(), "value:" + editDialog.getValue());
                        editDialog.dismiss();
                    }
                });
                editDialog.show();
            }
        });

        findViewById(R.id.bt_test_progress_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialogFactory.showRequestDialog(ViewTestActivity.this, "测试中...", false);
                Handler handler = new Handler(getMainLooper());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ProgressDialogFactory.hideRequestDialog();
                    }
                }, 3 * 1000);
            }
        });

        findViewById(R.id.bt_pick_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), PickerViewTestActivity.class));
            }
        });
    }

    private void showDialog() {
        final Dialog dialog = new Dialog(ViewTestActivity.this, R.style.Dialog_Fullscreen);
        Window window = dialog.getWindow();
        // 设置宽高
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        // 设置布局
        window.setContentView(R.layout.alertdialog);

        //设置在底部显示
        window.setGravity(Gravity.BOTTOM);

        //设置宽度和屏幕一样
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = display.getWidth(); //设置宽度
        dialog.getWindow().setAttributes(lp);

        dialog.setCanceledOnTouchOutside(true);//点击外面消失
        // 设置监听
        Button ok = (Button) window.findViewById(R.id.btn_ok);
        Button cancel = (Button) window.findViewById(R.id.btn_cancel);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showText(getApplicationContext(), "确定");
                dialog.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showText(getApplicationContext(), "取消");
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public View Obtain(CustomMenuItem item, LayoutInflater layoutInflater) {
        TextView tv = new TextView(this);
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(18);
        tv.setTextColor(getResources().getColor(R.color.black_deep));
        tv.setText(item.getTitle());
        tv.setBackgroundResource(R.drawable.menu_item_bg);
        tv.setClickable(true);
        tv.setHeight(DeviceUtils.dip2px(getApplicationContext(), 50));
        return tv;
    }

    @Override
    public void dismiss() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case 1:
                ToastUtils.showText(getApplicationContext(), "菜单1");
                break;
            case 2:
                ToastUtils.showText(getApplicationContext(), "菜单2");
                break;
            case 3:
                ToastUtils.showText(getApplicationContext(), "菜单3");
                break;
        }
    }
}

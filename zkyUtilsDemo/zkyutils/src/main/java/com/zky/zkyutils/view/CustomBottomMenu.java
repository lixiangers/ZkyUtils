package com.zky.zkyutils.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.zky.zkyutils.R;
import com.zky.zkyutils.utils.DeviceUtils;

import java.util.List;

public class CustomBottomMenu extends PopupWindow implements OnClickListener {
    private RelativeLayout mRootView;
    private LinearLayout mItemLayout;
    private Context mContext;
    private MenuItemCallBack itemCallBack;
    private LayoutInflater layoutInflater;

    public CustomBottomMenu(Activity context, MenuItemCallBack itemsOnClick) {
        super(context);
        this.mContext = context;
        this.itemCallBack = itemsOnClick;
        mRootView = new RelativeLayout(context);
        mRootView.setGravity(Gravity.BOTTOM);
        mItemLayout = new LinearLayout(context);
        mItemLayout.setOrientation(LinearLayout.VERTICAL);
        RelativeLayout.LayoutParams ll = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        mRootView.addView(mItemLayout, ll);
        mItemLayout.setBackgroundResource(R.color.my_white);
        this.setContentView(mRootView);
        this.setWidth(LayoutParams.MATCH_PARENT);
        this.setHeight(LayoutParams.MATCH_PARENT);
        this.setFocusable(true);
        this.setAnimationStyle(R.style.AnimBottom);

        ColorDrawable dw = new ColorDrawable(0x7f000000);// 全部的背景
        this.setBackgroundDrawable(dw);

        mRootView.setOnTouchListener(new OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = mItemLayout.getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });
        layoutInflater = LayoutInflater.from(context);
    }

    public void setMenuList(List<CustomMenuItem> itemList) {
        mItemLayout.removeAllViews();
        for (int i = 0; i < itemList.size(); i++) {
            View item = itemCallBack.Obtain(itemList.get(i), layoutInflater);
            item.setId(itemList.get(i).getId());
            item.setOnClickListener(this);
            mItemLayout.addView(item, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            if (i != itemList.size() - 1) {
                View child = new View(this.mContext);
                child.setBackgroundResource(R.color.my_split_view_color);
                mItemLayout.addView(child, new LayoutParams(LayoutParams.MATCH_PARENT, 1));
            }
        }
    }

    public void show() {
        this.showAtLocation(mRootView, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        mRootView.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.push_bottom_in));
    }

    @Override
    public void onClick(View v) {
        itemCallBack.onClick(v);
        dismiss();
    }
}

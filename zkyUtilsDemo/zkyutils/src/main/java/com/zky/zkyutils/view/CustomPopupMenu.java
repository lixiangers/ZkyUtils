package com.zky.zkyutils.view;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.zky.zkyutils.R;

import java.util.ArrayList;

/**
 * @author yangyu
 */
public class CustomPopupMenu extends PopupWindow {
    private final int[] mLocation = new int[2];
    private Context mContext;
    private Rect mRect = new Rect();

    private boolean mIsDirty;

    private OnItemOnClickListener mItemOnClickListener;

    private ListView mListView;

    private ArrayList<CustomMenuItem> mActionItems = new ArrayList<CustomMenuItem>();

    public CustomPopupMenu(Context context) {
        this(context, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    public CustomPopupMenu(Context context, int width, int height) {
        this.mContext = context;

        setFocusable(true);
        setTouchable(true);
        setOutsideTouchable(true);

        setWidth(width);
        setHeight(height);

        setBackgroundDrawable(new BitmapDrawable());

        setContentView(LayoutInflater.from(mContext).inflate(R.layout.title_popup, null));
        this.setAnimationStyle(R.style.AnimationPreview);
        initUI();
    }

    /**
     *
     */
    private void initUI() {
        mListView = (ListView) getContentView().findViewById(R.id.title_list);

        mListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int index, long arg3) {
                dismiss();

                if (mItemOnClickListener != null)
                    mItemOnClickListener.onItemClick(mActionItems.get(index), index);
            }
        });
    }

    /**
     *
     */
    public void show(View view) {
        view.getLocationOnScreen(mLocation);

        mRect.set(mLocation[0], mLocation[1], mLocation[0] + view.getWidth(), mLocation[1] + view.getHeight());

        if (mIsDirty) {
            populateActions();
        }
        this.showAsDropDown(view, view.getLayoutParams().width / 2, 1);
    }

    /**
     *
     */
    private void populateActions() {
        mIsDirty = false;

        mListView.setAdapter(new BaseAdapter() {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                final Holder holder;
                if (convertView == null) {
                    holder = new Holder();
                    convertView = LayoutInflater.from(mContext).inflate(
                            R.layout.view_popup_menu_item, null);

                    holder.avatar = (ImageView) convertView.findViewById(R.id.iv_icon);
                    holder.name = (TextView) convertView.findViewById(R.id.tv_text);
                    convertView.setTag(holder);
                } else {
                    holder = (Holder) convertView.getTag();
                }

                CustomMenuItem item = mActionItems.get(position);

                holder.avatar.setImageResource(item.getIconId());
                holder.name.setText(item.getTitle());

                return convertView;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public Object getItem(int position) {
                return mActionItems.get(position);
            }

            @Override
            public int getCount() {
                return mActionItems.size();
            }
        });
    }

    /**
     *
     */
    public void addAction(CustomMenuItem action) {
        if (action != null) {
            mActionItems.add(action);
            mIsDirty = true;
        }
    }

    /**
     *
     */
    public void cleanAction() {
        if (mActionItems.isEmpty()) {
            mActionItems.clear();
            mIsDirty = true;
        }
    }

    /**
     *
     */
    public CustomMenuItem getAction(int position) {
        if (position < 0 || position > mActionItems.size())
            return null;
        return mActionItems.get(position);
    }

    /**
     *
     */
    public void setItemOnClickListener(OnItemOnClickListener onItemOnClickListener) {
        this.mItemOnClickListener = onItemOnClickListener;
    }

    /**
     * @author yangyu
     */
    public static interface OnItemOnClickListener {
        public void onItemClick(CustomMenuItem item, int position);
    }

    class Holder {
        private ImageView avatar;
        private TextView name;

    }
}

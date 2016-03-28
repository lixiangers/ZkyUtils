package com.zky.zkyutilsdemo;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class CustomArrayAdapter extends ArrayAdapter<DemoDetails> {
    public CustomArrayAdapter(Context context, DemoDetails[] demos) {
        super(context, R.layout.feature, R.id.title, demos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FeatureView featureView;
        if (convertView instanceof FeatureView) {
            featureView = (FeatureView) convertView;
        } else {
            featureView = new FeatureView(getContext());
        }
        DemoDetails demo = getItem(position);
        featureView.setTitleId(demo.titleId);
        featureView.setDescriptionId(demo.descriptionId);
        return featureView;
    }
}

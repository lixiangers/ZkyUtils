/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zky.zkyutilsdemo;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.zky.zkyutilsdemo.app.BackgroundTaskActivity;
import com.zky.zkyutilsdemo.app.ScanTestActivity;
import com.zky.zkyutilsdemo.app.ViewTestActivity;
import com.zky.zkyutilsdemo.app.HttpActivity;
import com.zky.zkyutilsdemo.app.customView.MultiScreenActivity;
import com.zky.zkyutilsdemo.app.customView.SlideCutlistViewActivity;
import com.zky.zkyutilsdemo.app.customView.TouchEventActivity;

public class MainActivity extends ListActivity {

    private static class DemoDetails {
        private final int titleId;
        private final int descriptionId;
        private final Class<? extends android.app.Activity> activityClass;

        public DemoDetails(int titleId, int descriptionId,
                           Class<? extends android.app.Activity> activityClass) {
            super();
            this.titleId = titleId;
            this.descriptionId = descriptionId;
            this.activityClass = activityClass;
        }
    }

    private static class CustomArrayAdapter extends ArrayAdapter<DemoDetails> {
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

    private static final DemoDetails[] demos = {
            new DemoDetails(R.string.http, R.string.http_description,
                    HttpActivity.class),
            new DemoDetails(R.string.background_task,
                    R.string.background_task_description,
                    BackgroundTaskActivity.class),
            new DemoDetails(R.string.view,
                    R.string.view_description,
                    ViewTestActivity.class),
            new DemoDetails(R.string.scan,
                    R.string.scan_description,
                    ScanTestActivity.class),
            new DemoDetails(R.string.customer_test,
                    R.string.customer_test,
                    MultiScreenActivity.class),
            new DemoDetails(R.string.touch_event_test,
                    R.string.touch_event_test,
                    TouchEventActivity.class),
            new DemoDetails(R.string.slide_list_view,
                    R.string.slide_list_view,
                    SlideCutlistViewActivity.class)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("ZkyUtilsDemo");
        ListAdapter adapter = new CustomArrayAdapter(
                this.getApplicationContext(), demos);
        setListAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        System.exit(0);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        DemoDetails demo = (DemoDetails) getListAdapter().getItem(position);
        startActivity(new Intent(this.getApplicationContext(),
                demo.activityClass));
    }
}

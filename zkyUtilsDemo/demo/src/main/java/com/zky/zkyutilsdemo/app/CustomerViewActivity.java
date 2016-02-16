package com.zky.zkyutilsdemo.app;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.zky.zkyutilsdemo.CustomArrayAdapter;
import com.zky.zkyutilsdemo.DemoDetails;
import com.zky.zkyutilsdemo.R;
import com.zky.zkyutilsdemo.app.customView.MultiScreenActivity;
import com.zky.zkyutilsdemo.app.customView.PathViewActivity;
import com.zky.zkyutilsdemo.app.customView.SlideCutlistViewActivity;

public class CustomerViewActivity extends ListActivity {
    private static final DemoDetails[] demos = {
            new DemoDetails(R.string.customer_test,
                    R.string.customer_test,
                    MultiScreenActivity.class),
            new DemoDetails(R.string.slide_list_view,
                    R.string.slide_list_view,
                    SlideCutlistViewActivity.class),
            new DemoDetails(R.string.path_view,
                    R.string.path_view,
                    PathViewActivity.class),
            new DemoDetails(R.string.test_surface_view,
                    R.string.test_surface_view,
                    TestSurfaceViewActivity.class)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListAdapter adapter = new CustomArrayAdapter(
                this.getApplicationContext(), demos);
        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        DemoDetails demo = (DemoDetails) getListAdapter().getItem(position);
        startActivity(new Intent(this.getApplicationContext(),
                demo.activityClass));
    }
}

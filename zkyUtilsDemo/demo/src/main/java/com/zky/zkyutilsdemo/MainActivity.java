package com.zky.zkyutilsdemo;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.zky.zkyutils.http.VolleryRequestSender;
import com.zky.zkyutilsdemo.http.CheckVersionResponse;
import com.zky.zkyutilsdemo.http.VersionRequest;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.bt_check_version).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VersionRequest versionRequest = new VersionRequest(new Response.Listener<CheckVersionResponse>() {
                    @Override
                    public void onResponse(CheckVersionResponse response) {
                        Toast.makeText(getApplication(), response.update == 1 ? "需要升级" : "最新版本", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplication(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

                versionRequest.version = "1.0.0";
                VolleryRequestSender.getInstance(MyApplication.instace).send(versionRequest);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

package com.daejeonpeople.activities;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.widget.TabHost;

import com.daejeonpeople.R;


//동규

public class Main extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        TabHost tabHost = (TabHost) findViewById(R.id.tabHost1);
        tabHost.setup();

        TabHost.TabSpec ts1 = tabHost.newTabSpec("Tab1");
        ts1.setIndicator("TAB 1", getResources().getDrawable(R.drawable.ic_home));
        ts1.setContent(R.id.tab1);
        tabHost.addTab(ts1);

        TabHost.TabSpec ts2 = tabHost.newTabSpec("Tab2");
        ts2.setIndicator("TAB 1", getResources().getDrawable(R.drawable.ic_category));
        ts2.setContent(R.id.tab2);
        tabHost.addTab(ts2);

        tabHost.setCurrentTab(0);
    }
}
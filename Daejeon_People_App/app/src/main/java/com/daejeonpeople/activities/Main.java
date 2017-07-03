package com.daejeonpeople.activities;

import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.widget.TabHost;

import com.daejeonpeople.R;


//동규

public class Main extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        TabHost tabHost1 = (TabHost) findViewById(R.id.tabHost1);
        tabHost1.setup();

        TabHost.TabSpec ts1 = tabHost1.newTabSpec("Tab Spec 1");
        ts1.setContent(R.id.tab1);
        ts1.setIndicator("TAB 1");
        tabHost1.addTab(ts1);

        TabHost.TabSpec ts2 = tabHost1.newTabSpec("Tab Spec 2");
        ts2.setContent(R.id.tab2);
        ts1.setIndicator("TAB 2");
        tabHost1.addTab(ts2);



//        TabHost tabHost = getTabHost();
//
//        TabHost.TabSpec tabSpecTab1 = tabHost.newTabSpec("TAB1").setIndicator("메인으로");
//        tabSpecTab1.setContent(R.id.tab1);
//        tabHost.addTab(tabSpecTab1);
//
//        TabHost.TabSpec tabSpecTab2 = tabHost.newTabSpec("TAB2").setIndicator("카테고리");
//        tabSpecTab2.setContent(R.id.tab2);
//        tabHost.addTab(tabSpecTab2);


    }
}
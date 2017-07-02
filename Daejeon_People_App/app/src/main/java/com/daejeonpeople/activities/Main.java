package com.daejeonpeople.activities;

import android.app.TabActivity;
import android.os.Bundle;
import android.content.Intent;
import android.widget.TabHost;

import com.daejeonpeople.R;


//동규

public class Main extends TabActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        startActivity(new Intent(this,Splash.class));

        TabHost tabHost = getTabHost();

        TabHost.TabSpec tabSpecTab1 = tabHost.newTabSpec("TAB1").setIndicator("메인으로");
        tabSpecTab1.setContent(R.id.tab1);
        tabHost.addTab(tabSpecTab1);

        TabHost.TabSpec tabSpecTab2 = tabHost.newTabSpec("TAB2").setIndicator("카테고리");
        tabSpecTab2.setContent(R.id.tab2);
        tabHost.addTab(tabSpecTab2);


    }
}
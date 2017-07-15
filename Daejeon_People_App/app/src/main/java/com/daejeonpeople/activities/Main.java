package com.daejeonpeople.activities;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.Toast;
import com.daejeonpeople.R;
import com.daejeonpeople.activities.base.BaseActivity;
import com.daejeonpeople.adapter.CustomAdapter;
import com.daejeonpeople.adapter.CustomsAdapter;

//동규

public class Main extends BaseActivity {
    ViewPager pager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);

        //Bottom tabwidget & tabhost Code
        TabHost tabHost = (TabHost) findViewById(R.id.tabHost1);
        tabHost.setup();

        //tabwidget에 사진 넣기가 너무 어렵네요.ㅠㅠ 죄송합니다.
        TabHost.TabSpec spec = tabHost.newTabSpec("Tab1");
        spec.setContent(R.id.tab1);
        spec.setIndicator("메인으로",
                this.getResources().getDrawable(R.drawable.main_items));
        tabHost.addTab(spec);

        spec = tabHost.newTabSpec("Tab2");
        spec.setContent(R.id.tab2);
        spec.setIndicator("카테고리",
                this.getResources().getDrawable(R.drawable.main_items));
        tabHost.addTab(spec);




//        TabHost.TabSpec ts1 = tabHost.newTabSpec("Tab1");
//        ts1.setIndicator("메인으로", ContextCompat.getDrawable( getApplicationContext() ,R.drawable.ic_home));
//        ts1.setContent(R.id.tab1);
//        tabHost.addTab(ts1);
//
//        TabHost.TabSpec ts2 = tabHost.newTabSpec("Tab2");
//        ts2.setIndicator("카테고리", ContextCompat.getDrawable( getApplicationContext() ,R.drawable.ic_category));
//        ts2.setContent(R.id.tab2);
//        tabHost.addTab(ts2);

        tabHost.setCurrentTab(0);

        //Actionbat Code
        final ActionBar main = getActionBar();
        main.setCustomView(R.layout.custom_main);
        main.setDisplayShowTitleEnabled(false);
        main.setDisplayShowCustomEnabled(true);
        main.setDisplayShowHomeEnabled(false);

        //Advertising ViewPager
        pager= (ViewPager)findViewById(R.id.pager1);
        CustomAdapter adapter= new CustomAdapter(getLayoutInflater());
        pager.setAdapter(adapter);

        //이달의 인기만점 ViewPager
        pager= (ViewPager)findViewById(R.id.pager2);
        CustomsAdapter adapter2= new CustomsAdapter(getLayoutInflater());
        pager.setAdapter(adapter2);

        //이달이 가볼만한 곳 ViewPager
        pager= (ViewPager)findViewById(R.id.pager3);
        CustomsAdapter adapter3= new CustomsAdapter(getLayoutInflater());
        pager.setAdapter(adapter3);

        findViewById(R.id.ic_menu).setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), "토스트메시지입니다.", Toast.LENGTH_SHORT).show();
                    }
                }
        );

    }
}
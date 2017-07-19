package com.daejeonpeople.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.Toast;

import com.daejeonpeople.R;
import com.daejeonpeople.activities.base.BaseActivity;
import com.daejeonpeople.adapter.CustomAdapter;
import com.daejeonpeople.adapter.CustomsAdapter;

//동규

public class Main extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //Bottom tabwidget & tabhost Code
        TabHost tabHost = (TabHost) findViewById(R.id.tabHost1);
        tabHost.setup();

        //tabwidget에 사진 넣기가 너무 어렵네요.ㅠㅠ 죄송합니다.
        TabHost.TabSpec ts1 = tabHost.newTabSpec("Tab1");
        ts1.setIndicator("메인으로", ContextCompat.getDrawable( getApplicationContext() ,R.drawable.ic_home));
        ts1.setContent(R.id.tab1);
        tabHost.addTab(ts1);

        TabHost.TabSpec ts2 = tabHost.newTabSpec("Tab2");
        ts2.setIndicator("카테고리", ContextCompat.getDrawable( getApplicationContext() ,R.drawable.ic_category));
        ts2.setContent(R.id.tab2);
        tabHost.addTab(ts2);

        tabHost.setCurrentTab(0);

//        //Actionbar Code
//        final ActionBar main = getActionBar();
//        main.setCustomView(R.layout.custom_main);
//        main.setDisplayShowTitleEnabled(false);
//        main.setDisplayShowCustomEnabled(true);
//        main.setDisplayShowHomeEnabled(false);

        //Advertising ViewPager
        pager = (ViewPager) findViewById(R.id.pager1);
        CustomAdapter adapter = new CustomAdapter(getLayoutInflater());
        pager.setAdapter(adapter);

        //이달의 인기만점 ViewPager
        pager = (ViewPager) findViewById(R.id.pager2);
        CustomsAdapter adapter2 = new CustomsAdapter(getLayoutInflater());
        pager.setAdapter(adapter2);

        //이달이 가볼만한 곳 ViewPager
        pager = (ViewPager) findViewById(R.id.pager3);
        CustomsAdapter adapter3 = new CustomsAdapter(getLayoutInflater());
        pager.setAdapter(adapter3);

//        findViewById(R.id.ic_menu).setOnClickListener(
//                new Button.OnClickListener() {
//                    public void onClick(View v) {
//                        Toast.makeText(getApplicationContext(), "토스트메시지입니다.", Toast.LENGTH_SHORT).show();
//                    }
//                }
//        );

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}
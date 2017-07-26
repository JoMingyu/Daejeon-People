package com.daejeonpeople.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
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
import android.view.MenuItem;
import android.widget.TabHost;

import com.daejeonpeople.R;
import com.daejeonpeople.adapter.CustomAdapter;
import com.daejeonpeople.adapter.CustomsAdapter;
import com.daejeonpeople.support.views.SnackbarManager;
import com.daejeonpeople.support.database.DBHelper;

//동규

public class Main extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);

        //Bottom tabwidget & tabhost Code
        TabHost tabHost = (TabHost) findViewById(R.id.tabHost1);
        tabHost.setup();

        //메인으로 위젯 생성
        TabHost.TabSpec ts1 = tabHost.newTabSpec("Tab1");
        ts1.setIndicator("메인으로", ContextCompat.getDrawable( getApplicationContext() ,R.drawable.ic_home));
        ts1.setContent(R.id.tab1);
        tabHost.addTab(ts1);

        //카테고리 위젯 생성
        TabHost.TabSpec ts2 = tabHost.newTabSpec("Tab2");
        ts2.setIndicator("카테고리", ContextCompat.getDrawable( getApplicationContext() ,R.drawable.ic_category));
        ts2.setContent(R.id.tab2);
        tabHost.addTab(ts2);
        //tabwidget에 사진 넣기가 너무 어렵네요.ㅠㅠ 죄송합니다.
        tabHost.setCurrentTab(0);

        //Advertising ViewPager
        pager = (ViewPager) findViewById(R.id.pager1);
        CustomAdapter adapter = new CustomAdapter(getLayoutInflater());
        pager.setAdapter(adapter);

        //이달의 인기만점 ViewPager
        pager = (ViewPager) findViewById(R.id.pager2);
        CustomsAdapter adapter2 = new CustomsAdapter(getLayoutInflater());
        pager.setAdapter(adapter2);

        DBHelper dbHelper = DBHelper.getInstance(getApplicationContext(), "CHECK.db", null, 1);

        DrawerLayout drawer;
        Toolbar toolbar;
        ActionBarDrawerToggle toggle;
        NavigationView navigationView;

        //로그인이 되어 있는 경우
        if(dbHelper.getCookie() != null) {
            //툴바 생성 액션바 대신 툴바 사용합니다.
            toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);

            //사이드메뉴 생성
            drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();

            navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
        } //로그인이 되어 있지 않은 경우
          else {

//            //툴바 생성 액션바 대신 툴바 사용합니다.
//            toolbar = (Toolbar) findViewById(R.id.toolbar_not_login);
//            setSupportActionBar(toolbar);
//            getSupportActionBar().setDisplayShowTitleEnabled(false);
//
//            //사이드메뉴 생성
//            drawer = (DrawerLayout) findViewById(R.id.drawer_layout_not_login);
//            toggle = new ActionBarDrawerToggle(
//                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//            drawer.setDrawerListener(toggle);
//            toggle.syncState();
//
//            navigationView = (NavigationView) findViewById(R.id.nav_view_not_login);
//            navigationView.setNavigationItemSelectedListener(this);

            //툴바 생성 액션바 대신 툴바 사용합니다.
            toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);

            //사이드메뉴 생성
            drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();

            navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
        }
    }

    //Navigation onBackPressed
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // 네비게이션 뷰 조작
        switch(item.getItemId()) {
            case R.id.navigation_item01:
                // 내 정보
                SnackbarManager.createCancelableSnackbar(getWindow().getDecorView().getRootView(), "하이염").show();
                break;
            case R.id.navigation_item02:
                // 설정
                break;
            case R.id.nav_sub_menu_item01:
                // 위시리스트
                break;
            case R.id.nav_sub_menu_item02:
                // 친구 목록
                break;
            case R.id.nav_sub_menu_item03:
                // 최근여행지
                break;
            case R.id.nav_sub_menu_item04:
                // 활성화된 여행
                break;
            default:
                SnackbarManager.createCancelableSnackbar(getWindow().getDecorView().getRootView(), "비정상적인 접근입니다.").show();
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
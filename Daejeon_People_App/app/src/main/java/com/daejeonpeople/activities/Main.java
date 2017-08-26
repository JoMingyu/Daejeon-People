package com.daejeonpeople.activities;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.app.FragmentTransaction;
import android.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ActionBarOverlayLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.bumptech.glide.Glide;
import com.daejeonpeople.R;
import com.daejeonpeople.activities.side_menu.ChatList;
import com.daejeonpeople.activities.side_menu.FriendList;
import com.daejeonpeople.activities.side_menu.MyInfo;
import com.daejeonpeople.activities.side_menu.WishList;
import com.daejeonpeople.adapter.CustomAdapter;
import com.daejeonpeople.adapter.CustomsAdapter;
import com.daejeonpeople.support.database.DBHelper;
import com.daejeonpeople.support.network.SessionManager;
import com.daejeonpeople.support.views.SnackbarManager;
import com.mikepenz.materialdrawer.util.PressedEffectStateListDrawable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

//동규

public class Main extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
{
    ViewPager pager;
    FragmentManager fragmentManager;

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);

        //툴바 생성
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

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

        //카테고리 이미지 적용
        ImageView iv = (ImageView)findViewById(R.id.tourism);
        Glide.with(this).load(R.drawable.bg_tourism).into(iv);

        iv = (ImageView)findViewById(R.id.culture);
        Glide.with(this).load(R.drawable.bg_culture).into(iv);

        iv = (ImageView)findViewById(R.id.event);
        Glide.with(this).load(R.drawable.bg_event).into(iv);

        iv = (ImageView)findViewById(R.id.course);
        Glide.with(this).load(R.drawable.bg_course).into(iv);

        iv = (ImageView)findViewById(R.id.leisure);
        Glide.with(this).load(R.drawable.bg_leisure).into(iv);

        iv = (ImageView)findViewById(R.id.accommodation);
        Glide.with(this).load(R.drawable.bg_accommodation).into(iv);

        iv = (ImageView)findViewById(R.id.shopping);
        Glide.with(this).load(R.drawable.bg_shopping).into(iv);

        iv = (ImageView)findViewById(R.id.food);
        Glide.with(this).load(R.drawable.bg_food).into(iv);


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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        //사이드 메뉴
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //로그인 됨
        if(dbHelper.getCookie() != null) {
            navigationView.getMenu().setGroupVisible(R.id.login_group, true);
            navigationView.getMenu().setGroupVisible(R.id.not_login_group, false);
            navigationView.getMenu().findItem(R.id.navigation_item01).setVisible(true);
            navigationView.getMenu().findItem(R.id.navigation_item02).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_sub_menu_item01).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_sub_menu_item02).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_sub_menu_item03).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_sub_menu_item04).setVisible(true);
            navigationView.getMenu().findItem(R.id.navigation_not_login_item01).setVisible(false);
            navigationView.getMenu().findItem(R.id.navigation_not_login_item02).setVisible(false);
        }

        //로그인 안됨
        else {
            navigationView.getMenu().setGroupVisible(R.id.login_group, false);
            navigationView.getMenu().setGroupVisible(R.id.not_login_group, true);
            navigationView.getMenu().findItem(R.id.navigation_item01).setVisible(false);
            navigationView.getMenu().findItem(R.id.navigation_item02).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_sub_menu_item01).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_sub_menu_item02).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_sub_menu_item03).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_sub_menu_item04).setVisible(false);
            navigationView.getMenu().findItem(R.id.navigation_not_login_item01).setVisible(true);
            navigationView.getMenu().findItem(R.id.navigation_not_login_item02).setVisible(true);
            View header = navigationView.getHeaderView(0);
            TextView name = (TextView)header.findViewById(R.id.nameView);
            name.setText("익명 사용자");
            TextView email = (TextView)header.findViewById(R.id.emailView);
            email.setText("이메일 주소가 없습니다.");
            TextView phonenum = (TextView)header.findViewById(R.id.phoneNumberView);
            phonenum.setText("전화번호가 없습니다.");
            ImageView profileimg = (ImageView)header.findViewById(R.id.header_img);
            profileimg.setImageResource(R.drawable.ic_profile_dark);
        }

    }


    @Override
    public void onBackPressed() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            // 백스택이 존재할 떄까지
            if (fm.getBackStackEntryCount() > 0)
            {
                fm.popBackStack();
                ft.commit();
            }
            // 백스택이 없는 경우
            else
            {
                super.onBackPressed();
            }
        }
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;
        String title = getString(R.string.app_name);


        DBHelper dbHelper = DBHelper.getInstance(getApplicationContext(), "CHECK.db", null, 1);
        if(dbHelper.getCookie() != null) {
            // 네비게이션 뷰 조작
            switch(id) {
                case R.id.navigation_item01:
                    // 내 정보
                    fragment = new MyInfo();
                    title = "내정보";
                    break;
                case R.id.navigation_item02:
                    // 설정
//                startActivity(new Intent(this, ))
                    break;
                case R.id.nav_sub_menu_item01:
                    // 위시리스트
                    startActivity(new Intent(this, WishList.class));
                    break;
                case R.id.nav_sub_menu_item02:
                    // 친구 목록
                    startActivity(new Intent(this, FriendList.class));
                    break;
                case R.id.nav_sub_menu_item03:
                    // 최근여행지
                    break;
                case R.id.nav_sub_menu_item04:
                    // 활성화된 여행
                    startActivity(new Intent(this, ChatList.class));
                    break;
                default:
                    SnackbarManager.createCancelableSnackbar(getWindow().getDecorView().getRootView(), "비정상적인 접근입니다.").show();
                    break;
            }
        } else {
            // 네비게이션 뷰 조작
            switch(id) {
                case R.id.navigation_not_login_item01:
                    // 내 정보
                    fragment = new MyInfo();
                    title = "내정보";
                    break;
                case R.id.navigation_not_login_item02:
                    // 설정
//                startActivity(new Intent(this, ))
                    break;
                default:
                    SnackbarManager.createCancelableSnackbar(getWindow().getDecorView().getRootView(), "비정상적인 접근입니다.").show();
                    break;
            }
        }

        if(fragment != null) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.addToBackStack("sidemenufragment").add(R.id.content_fragment_layout, fragment);
            ft.commit();
        }

        if(getSupportActionBar() != null) {
            ((TextView) findViewById(R.id.toolbar_title)).setText(title);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

}
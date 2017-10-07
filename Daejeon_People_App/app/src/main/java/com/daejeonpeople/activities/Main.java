package com.daejeonpeople.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.app.FragmentTransaction;
import android.app.FragmentManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.daejeonpeople.R;
import com.daejeonpeople.activities.account.SignIn;
import com.daejeonpeople.activities.account.SignUp;
import com.daejeonpeople.activities.chatting.ChatList;
import com.daejeonpeople.activities.side_menu.MyInfo;
import com.daejeonpeople.activities.side_menu.WishList;
import com.daejeonpeople.support.database.DBHelper;
import com.daejeonpeople.support.network.APIClient;
import com.daejeonpeople.support.network.APIinterface;
import com.daejeonpeople.support.security.AES;
import com.daejeonpeople.support.views.SnackbarManager;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//동규

public class Main extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
{
    private TextView name, email, phonenum;
    private ImageView profileimg;
    private APIinterface apiInterface;
    private DBHelper dbHelper;
    private AES aes;

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);

        //툴바 생성
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        apiInterface = APIClient.getClient().create(APIinterface.class);
        dbHelper = DBHelper.getInstance(getApplicationContext(), "CHECK.db", null, 1);
        aes = new AES();

        final Main_fragment main_fragment;
        final Category_fragment category_fragment;

        main_fragment = new Main_fragment();
        category_fragment = new Category_fragment();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.tabcontent, main_fragment).commit();

        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("메인으로").setIcon(R.drawable.tabwidget01));
        tabs.addTab(tabs.newTab().setText("카테고리").setIcon(R.drawable.tabwidget02));

        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab){
             int position = tab.getPosition();

                android.support.v4.app.Fragment selected = null;
                if(position == 0){
                    selected = main_fragment;
                } else if(position == 1) {
                    selected = category_fragment;
                }

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.tabcontent, selected).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }


            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        //사이드 메뉴
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        name = (TextView) header.findViewById(R.id.nameView);
        email = (TextView) header.findViewById(R.id.emailView);
        phonenum = (TextView) header.findViewById(R.id.phoneNumberView);
        profileimg = (ImageView) header.findViewById(R.id.header_img);

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
            apiInterface.getMyPage("UserSession="+dbHelper.getCookie()).enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if(response.code() == 200){
                        name.setText(aes.decrypt(response.body().get("name").toString()));
                        email.setText(aes.decrypt(response.body().get("email").toString()));
                        phonenum.setText(response.body().get("phone_number").toString());
                    } else {
                        Log.d("error", "ang gi mo thi");
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    t.printStackTrace();
                }
            });
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
            name.setText("익명 사용자");
            email.setText("이메일 주소가 없습니다.");
            phonenum.setText("전화번호가 없습니다.");
            profileimg.setImageResource(R.drawable.ic_profile_dark);
        }

        //search버튼 이벤트
        Button search_btn = (Button) findViewById(R.id.ic_search);

        search_btn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                //검색창
                Intent intent=new Intent(Main.this, Search.class);
                startActivity(intent);
            }
        }) ;

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
        android.app.Fragment fragment = null;
        String title = getString(R.string.app_name);


        DBHelper dbHelper = DBHelper.getInstance(getApplicationContext(), "CHECK.db", null, 1);
        if(dbHelper.getCookie() != null) {
            // 네비게이션 뷰 조작
            switch(id) {
                case R.id.navigation_item01:
                    // 내 정보
                    fragment = new MyInfo();
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
                    startActivity(new Intent(this, AddressBook.class));
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
            Intent intent;
            switch(id) {
                case R.id.navigation_not_login_item01:
                    // 로그인
                    intent = new Intent(this, SignIn.class);
                    startActivity(intent);
                    break;
                case R.id.navigation_not_login_item02:
                    // 회원 가입
                    intent = new Intent(this, SignUp.class);
                    startActivity(intent);
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
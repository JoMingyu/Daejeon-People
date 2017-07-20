package com.daejeonpeople.activities.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.daejeonpeople.R;
import com.daejeonpeople.activities.Main;
import com.daejeonpeople.activities.base.BaseActivity;
import com.daejeonpeople.support.database.DBHelper;

/**
 * Created by 10102김동규 on 2017-05-10.
 */

public class Splash extends BaseActivity {
    @Override
    protected void onPause(){
        super.onPause();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        Handler hd = new Handler();
        hd.postDelayed(new Runnable() {
            @Override
            public void run() {
                judge();
            }
        }, 3000);
    }

    private void judge() {
        DBHelper dbHelper = DBHelper.getInstance(getApplicationContext(), "CHECK.db", null, 1);
        Intent SignIn = new Intent(getApplicationContext(), com.daejeonpeople.activities.account.SignIn.class);
        Intent SignUp = new Intent(getApplicationContext(), com.daejeonpeople.activities.account.SignUp.class);
        Intent Main = new Intent(getApplicationContext(), com.daejeonpeople.activities.Main.class);

        if(dbHelper.isFirstExecution()){
            dbHelper.firstExecution();
            startActivity(SignUp);
        } else {
            if(dbHelper.getCookie() != null) {
                startActivity(Main);
            } else {
                startActivity(SignIn);
            }
        }
    }
}

package com.daejeonpeople.activities;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;

import com.daejeonpeople.R;
import com.daejeonpeople.support.database.DBHelper;

/**
 * Created by 10102김동규 on 2017-05-10.
 */

public class Splash extends Activity{
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
        Intent SignIn = new Intent(getApplicationContext(), SignIn.class);
        Intent Main = new Intent(getApplicationContext(), Main.class);

        if(dbHelper.isFirstExecution()){
            //Start Step
        } else {
            if(dbHelper.isAutoLogined()) {
                startActivity(SignIn);
            } else {
                startActivity(Main);
            }
        }
    }
}

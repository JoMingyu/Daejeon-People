package com.daejeonpeople.activities;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

import com.daejeonpeople.R;

/**
 * Created by 10102김동규 on 2017-05-10.
 */
//둥규

public class Splash extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //// TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        Handler hd = new Handler();
        hd.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 3000);
    }
}
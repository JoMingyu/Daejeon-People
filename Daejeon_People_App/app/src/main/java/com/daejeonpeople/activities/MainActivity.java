package com.daejeonpeople.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.daejeonpeople.R;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invitation_trip_listview);
        startActivity(new Intent(this,Splash.class));
    }
}

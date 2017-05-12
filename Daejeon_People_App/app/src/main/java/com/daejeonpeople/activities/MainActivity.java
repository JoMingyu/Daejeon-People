package com.daejeonpeople.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.daejeonpeople.R;
import com.daejeonpeople.support.firebase.Firebase;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        startActivity(new Intent(this,Splash.class));
    }
}

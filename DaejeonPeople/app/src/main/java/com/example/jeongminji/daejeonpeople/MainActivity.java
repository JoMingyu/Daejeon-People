package com.example.jeongminji.daejeonpeople;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        startActivity(new Intent(this,Splash.class));
        //startActivity(new Intent(this,))
    }
}

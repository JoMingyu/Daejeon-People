package com.daejeonpeople.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.daejeonpeople.R;
import com.daejeonpeople.activities.account.SignIn;
import com.daejeonpeople.activities.account.SignUp;

/**
 * Created by geni on 2017. 8. 28..
 */

public class Landing extends AppCompatActivity {
    private Button signInBtn;
    private Button signUpBtn;
    private Button skipBtn;

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing);

        signInBtn = (Button)findViewById(R.id.signInBtn);
        signUpBtn = (Button)findViewById(R.id.signUpBtn);
        skipBtn = (Button)findViewById(R.id.skipBtn);

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SignIn.class));
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SignUp.class));
            }
        });

        skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Main.class));
            }
        });
    }
}

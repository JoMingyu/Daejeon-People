package com.daejeonpeople.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.daejeonpeople.R;
import com.daejeonpeople.connection.AqueryConnection;

import java.util.HashMap;
//민지

public class SignUp extends AppCompatActivity {
    AqueryConnection connection;
    HashMap<String, Object> params;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        submit = (Button)findViewById(R.id.signupSubmit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                connection = new AqueryConnection(getApplicationContext(), params, "signup");
            }
        });
    }
}

package com.daejeonpeople.activities;

import android.content.Intent;
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
    Button emailCertifiedBtn;
    int emailCertifiedCheck = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        submit = (Button)findViewById(R.id.signupSubmit);
        emailCertifiedBtn = (Button)findViewById(R.id.emailCertified);

        emailCertifiedBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(getApplicationContext(), Email_Certified.class));
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                connection = new AqueryConnection();
//                connection.connection(getApplicationContext(), params, "signup");
//                int serverStatus = connection.getStatusCode();
//                System.out.println(serverStatus);
//                if(serverStatus == 201){
//                    Intent emailCertifiedIntent = getIntent();
//                    emailCertifiedCheck = emailCertifiedIntent.getExtras().getInt("success");
//                    if(emailCertifiedCheck == 1){
//                        Intent intent = new Intent(getApplicationContext(), SignIn.class);
//                        startActivity(intent);
//                    }
//                }
            }
        });
    }
}

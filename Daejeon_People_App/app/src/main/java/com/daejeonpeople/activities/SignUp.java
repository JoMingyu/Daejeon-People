package com.daejeonpeople.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.daejeonpeople.R;
import com.daejeonpeople.connection.AqueryConnection;
import com.daejeonpeople.support.firebase.Firebase;

import java.util.HashMap;
//민지

public class SignUp extends AppCompatActivity {
    AqueryConnection connection;
    HashMap<String, Object> params = new HashMap<String, Object>();
    Button submit;
    Button emailCertifiedBtn;
    EditText userId;
    EditText userName;
    EditText userPassword;
    Firebase firebase;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        firebase = new Firebase();
        submit = (Button)findViewById(R.id.signupSubmit);
        emailCertifiedBtn = (Button)findViewById(R.id.emailCertified);
        connection = new AqueryConnection(getApplicationContext());
        emailCertifiedBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(getApplicationContext(), Email_Certified.class));
            }
        });

        userId = (EditText)findViewById(R.id.userId);
        userName = (EditText)findViewById(R.id.userName);
        userPassword = (EditText)findViewById(R.id.userPassword);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailCertifiedIntent = getIntent();
                email = emailCertifiedIntent.getExtras().getString("email");
                params.put("id", userId.getText().toString());
                params.put("password", userPassword.getText().toString());
                params.put("email", email);
                params.put("name", userName.getText().toString());
                params.put("registration_id", firebase.getFirebaseToken());
                connection.connection(params, "signup");
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    if(connection.isResponse == true){
                        int serverStatus = connection.getStatusCode();
                        System.out.println(serverStatus);
                        if(serverStatus == 201){
                            if(email != null){
                                Intent intent = new Intent(getApplicationContext(), SignIn.class);
                                startActivity(intent);
                            }
                        }
                        break;
                    }
                }
            }
        }).start();
    }
}

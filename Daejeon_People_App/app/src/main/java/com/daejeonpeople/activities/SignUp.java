package com.daejeonpeople.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.daejeonpeople.R;
import com.daejeonpeople.connection.connectionValues;
import com.daejeonpeople.support.firebase.Firebase;

import java.util.HashMap;
//민지

public class SignUp extends AppCompatActivity {
    AQuery aQuery;
    HashMap<String, Object> params = new HashMap<>();
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
        aQuery = new AQuery(getApplicationContext());
        submit = (Button)findViewById(R.id.signupSubmit);
        emailCertifiedBtn = (Button)findViewById(R.id.emailCertified);
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
                aQuery.ajax(connectionValues.URL + "/signup", params, String.class, new AjaxCallback<String>(){
                    @Override
                    public void callback(String url, String response, AjaxStatus status){
                        int statusCode = status.getCode();
                        if(statusCode == 201){
                            Intent intent = new Intent(getApplicationContext(), SignIn.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "회원가입 실패", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}

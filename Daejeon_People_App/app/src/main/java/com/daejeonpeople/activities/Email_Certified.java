package com.daejeonpeople.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.daejeonpeople.R;
import com.daejeonpeople.connection.AqueryConnection;

import java.util.HashMap;
import java.util.Objects;

//민지

public class Email_Certified extends AppCompatActivity {
    AqueryConnection connection;
    HashMap<String, Object> params = new HashMap<String, Object>();
    EditText email;
    EditText checkCode;
    Button confirmBtn;
    boolean overlapCheck = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.email_certified);
        confirmBtn = (Button)findViewById(R.id.confirmBtn);
        email = (EditText)findViewById(R.id.userEmail);
        connection = new AqueryConnection(getApplicationContext());

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(overlapCheck == false){
                    params.put("email", email.getText().toString());
                    connection.connection(params, "signup/email/demand");
                    ShowDialog();
                    System.out.println(overlapCheck);
                } else if(overlapCheck == true){
                    System.out.println("wow");
                    checkCode = (EditText)findViewById(R.id.checkCode);
                    params.put("code", checkCode.getText());
                    connection.connection(params, "signup/email/verify");
                    int statusCode = connection.getStatusCode();
                    if(statusCode == 201){
                        Intent intent = new Intent(getApplicationContext(), SignUp.class);
                        intent.putExtra("success", 1);
                        startActivity(intent);
                    }
                }
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    if(connection.isResponse == true){
                        handler.sendEmptyMessage(0);
                        break;
                    }
                }
            }
        }).start();
    }

    final Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 0){
                int code = connection.getStatusCode();
                System.out.println(code);
                if(code == 201){
                    confirmBtn.setText("확인");
                    overlapCheck = true;
                } else if(code == 204){
                    Toast.makeText(getApplicationContext(), "이미 존재하는 이메일입니다", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    private void ShowDialog()
    {
        LayoutInflater dialog = LayoutInflater.from(this);
        final View dialogLayout = dialog.inflate(R.layout.email_certified2, null);
        final Dialog myDialog = new Dialog(this);

        myDialog.setTitle("EmailCertified2");
        myDialog.setContentView(dialogLayout);
        myDialog.show();

        Button btn_ok = (Button)dialogLayout.findViewById(R.id.btn_ok);
        Button btn_cancel = (Button)dialogLayout.findViewById(R.id.btn_cancel);

        btn_ok.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                myDialog.cancel();
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                myDialog.cancel();
            }
        });
    }
}


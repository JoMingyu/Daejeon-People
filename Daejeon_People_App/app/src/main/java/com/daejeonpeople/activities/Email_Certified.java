package com.daejeonpeople.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.daejeonpeople.R;
import com.daejeonpeople.connection.connectionValues;

import java.util.HashMap;

//민지

public class Email_Certified extends AppCompatActivity {
    AQuery aQuery;
    HashMap<String, Object> params = new HashMap<>();
    EditText email;
    EditText checkCode;
    Button confirmBtn;
    int statusCode;
    boolean emailDemandCheck = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.email_certified);
        confirmBtn = (Button)findViewById(R.id.confirmBtn);
        email = (EditText)findViewById(R.id.userEmail);

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(emailDemandCheck == false){
                    params.put("email", email.getText().toString());
                    aQuery = new AQuery(getApplicationContext());
                    System.out.println(connectionValues.URL);
                    aQuery.ajax("http://52.79.134.200/signup/email/demand", params, String.class, new AjaxCallback<String>(){
                        @Override
                        public void callback(String url, String response, AjaxStatus status) {
                            System.out.println(status.getCode());
                            statusCode = status.getCode();
                            if(statusCode == 201){
                                emailDemandCheck = true;
                                ShowDialog();
                            } else if(statusCode == 204){
                                Toast.makeText(getApplicationContext(), "이미 존재하는 이메일입니다.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else if(emailDemandCheck == true){
                    Log.d("emailDemandCheck", "true");
                    checkCode = (EditText)findViewById(R.id.checkCode);
                    params.put("code",checkCode.getText().toString());
                    System.out.println(params);
                    aQuery.ajax("http://52.79.134.200:80/signup/email/verify", params, String.class, new AjaxCallback<String>(){
                       @Override
                        public void callback(String url, String response, AjaxStatus status){
                            if(status.getCode() == 201){
                                Intent intent = new Intent(getApplicationContext(), SignUp.class);
                                intent.putExtra("email", email.getText().toString());
                                startActivity(intent);
                            }
                       }
                    });
                }
            }
        });
    }

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


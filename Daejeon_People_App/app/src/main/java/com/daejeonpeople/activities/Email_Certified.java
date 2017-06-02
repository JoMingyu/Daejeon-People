package com.daejeonpeople.activities;

import android.app.Dialog;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.email_certified);

        Button findBtn = (Button)findViewById(R.id.btn_find);
        email = (EditText)findViewById(R.id.userEmail);
        findBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                connection = new AqueryConnection();
                if(connection.connection(getApplicationContext(), params, "signup/email/check") == 201){
                    connection.connection(getApplicationContext(), params, "signup/email/demand");
                } else if(connection.connection(getApplicationContext(), params, "signup/email/check") == 204){
                    Toast.makeText(getApplicationContext(), "실패", Toast.LENGTH_SHORT).show();
                } else {
                    System.out.println("실패");
                }
                ShowDialog();
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


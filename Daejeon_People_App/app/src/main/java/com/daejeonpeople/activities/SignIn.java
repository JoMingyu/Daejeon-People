package com.daejeonpeople.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.daejeonpeople.R;
import com.daejeonpeople.connection.connectionValues;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 10102김동규 on 2017-05-11.
 */
// Modified by JoMingyu

public class SignIn extends Activity{
    private AQuery aQuery;

    private Button submitBtn;
    private EditText userId;
    private EditText userPassword;

    private TextView signUp;
    private TextView findId;
    private TextView findPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);

        submitBtn = (Button) findViewById(R.id.signinSubmit);
        userId = (EditText) findViewById(R.id.userId);
        userPassword = (EditText) findViewById(R.id.userPassword);

        signUp = (TextView) findViewById(R.id.signUp);
        findId = (TextView) findViewById(R.id.findId);
        findPassword = (TextView) findViewById(R.id.findPassword);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aQuery = new AQuery(getApplicationContext());

                String id = userId.getText().toString();
                String password = userPassword.getText().toString();

                if(!id.isEmpty() && !password.isEmpty()) {
                    Map<String, Object> params = new HashMap<>();

                    params.put("id", id);
                    params.put("password", password);
                    params.put("keep_login", false);

                    aQuery.ajax("http://52.79.134.200/signin", params, String.class, new AjaxCallback<String>(){
                        @Override
                        public void callback(String url, String response, AjaxStatus status){
                            int statusCode = status.getCode();
                            if(statusCode == 201) {
                                Intent intent = new Intent(getApplicationContext(), Main.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(), "ID나 비밀번호를 확인하세요.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

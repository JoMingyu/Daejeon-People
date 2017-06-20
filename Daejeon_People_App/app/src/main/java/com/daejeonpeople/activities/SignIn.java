package com.daejeonpeople.activities;

import android.app.Activity;
import android.content.Intent;
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

import java.util.HashMap;

/**
 * Created by 10102김동규 on 2017-05-11.
 */
//동규

public class SignIn extends Activity{
    AQuery aQuery;
    HashMap<String, Object> params = new HashMap<>();
    Button submit;
    EditText id;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);
        submit = (Button)findViewById(R.id.signinSubmit);
        id = (EditText)findViewById(R.id.userId);
        password = (EditText)findViewById(R.id.userPassword);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aQuery = new AQuery(getApplicationContext());
                params.put("id", id.getText().toString());
                params.put("password", password.getText().toString());
                params.put("keep_login", false);

                if(id.getText() != null && password.getText() != null){
                    aQuery.ajax(connectionValues.URL + "/signin", params, String.class, new AjaxCallback<String>(){
                        @Override
                        public void callback(String url, String response, AjaxStatus status){
                            int statusCode = status.getCode();
                            if(statusCode == 201){
                                Toast.makeText(getApplicationContext(), "로그인 성공", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), Main.class);
                                startActivity(intent);
                            } else if(statusCode == 204){
                                Toast.makeText(getApplicationContext(), "로그인 실패", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else if(id.getText() == null){
                    Toast.makeText(getApplicationContext(), "아이디를 입력해주세요", Toast.LENGTH_SHORT).show();
                } else if(password.getText() == null){
                    Toast.makeText(getApplicationContext(), "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "아이디, 비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

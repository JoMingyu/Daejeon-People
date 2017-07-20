package com.daejeonpeople.activities.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.daejeonpeople.R;
import com.daejeonpeople.activities.Main;
import com.daejeonpeople.activities.base.BaseActivity;
import com.daejeonpeople.support.network.SessionManager;
import com.daejeonpeople.support.views.SnackbarManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 10102김동규 on 2017-05-11.
 */
// Modified by JoMingyu

public class SignIn extends BaseActivity {
    private AQuery aQuery;

    private Button submitBtn;
    private EditText userId;
    private EditText userPassword;

    private CheckBox keepLoginBox;

    private TextView signUpView;
    private TextView findIdView;
    private TextView findPasswordView;

    private boolean needFinish;

    @Override
    protected void onPause() {
        super.onPause();
        if(needFinish) {
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);

        needFinish = false;

        signUpView = (TextView) findViewById(R.id.signUpView);
        findIdView = (TextView) findViewById(R.id.findIdView);
        findPasswordView = (TextView) findViewById(R.id.findPasswordView);

        keepLoginBox = (CheckBox) findViewById(R.id.keepLoginBox);

        submitBtn = (Button) findViewById(R.id.okBtn);
        userId = (EditText) findViewById(R.id.inputId);
        userPassword = (EditText) findViewById(R.id.inputPw);

        signUpView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUp.class);
                startActivity(intent);
            }
        });

        findIdView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FindID.class);
                startActivity(intent);
            }
        });

        findPasswordView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FindPW.class);
                startActivity(intent);
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                aQuery = new AQuery(getApplicationContext());

                String id = userId.getText().toString();
                String password = userPassword.getText().toString();
                final boolean keepLogin = keepLoginBox.isChecked();

                if(!id.isEmpty() && !password.isEmpty()) {
                    Map<String, Object> params = new HashMap<>();

                    params.put("id", id);
                    params.put("password", password);
                    params.put("keep_login", keepLogin);

                    aQuery.ajax("http://52.79.134.200/signin", params, String.class, new AjaxCallback<String>(){
                        @Override
                        public void callback(String url, String response, AjaxStatus status){
                            int statusCode = status.getCode();
                            if(statusCode == 201) {
                                if(keepLogin) {
                                    String cookie = new SessionManager(status).detectCookie("UserSession");
                                    SessionManager.setCookieToDB(getApplicationContext(), cookie);
                                }

                                SnackbarManager.createCancelableSnackbar(v, "로그인 성공").show();
                                Intent intent = new Intent(getApplicationContext(), Main.class);
                                needFinish = true;
                                startActivity(intent);
                            } else {
                                SnackbarManager.createCancelableSnackbar(v, "아이디나 비밀번호를 확인하세요.").show();
                            }
                        }
                    });
                } else {
                    SnackbarManager.createCancelableSnackbar(v, "아이디나 비밀번호를 확인하세요.").show();
                }
            }
        });
    }
}

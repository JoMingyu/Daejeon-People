package com.daejeonpeople.activities;

import android.content.Intent;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.daejeonpeople.valueobject.User;

import java.util.HashMap;
import java.util.Map;
//민지

public class SignUp extends AppCompatActivity {
    private AQuery aQuery;
    private Button submitBtn;
    private Button emailCertifiedBtn;

    private EditText userName;

    private EditText userId;

    private EditText userPassword;
    private EditText passwordConfirm;

    private Firebase firebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        firebase = new Firebase();
        aQuery = new AQuery(getApplicationContext());

        submitBtn = (Button) findViewById(R.id.signupSubmit);
        emailCertifiedBtn = (Button) findViewById(R.id.emailCertified);

        userName = (EditText) findViewById(R.id.userName);
        userId = (EditText) findViewById(R.id.userId);
        userPassword = (EditText) findViewById(R.id.userPassword);
        passwordConfirm = (EditText) findViewById(R.id.passwordConfirm);

        if(User.emailCertified) {
            // 이메일 인증이 완료됐다면 버튼 컬러 변경
            emailCertifiedBtn.setTextColor(Color.rgb(111, 186, 119));
            Snackbar.make(getWindow().getDecorView().getRootView(), "이메일 인증 완료", 3000).show();
        }

        emailCertifiedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 인증 액티비티로 분기
                startActivity(new Intent(getApplicationContext(), Email_Certified.class));
            }
        });

        userName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && !userName.getText().toString().isEmpty()) {
                    // 이름이 1글자 이상일 때. 명시적
                    userName.setTextColor(Color.rgb(111, 186, 119));
                    User.name = userName.getText().toString();
                    User.nameChecked = true;
                } else {
                    // 포커스가 다시 바뀌는 경우, 이름이 비어있는 경우를 방지
                    User.nameChecked = false;
                }
            }
        });

        userId.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                User.id = userId.getText().toString();
                if (!hasFocus) {
                    // Focus가 넘어갈 때
                    if(!User.id.isEmpty()) {
                        // EditText가 비어있지 않을 때

                        Map<String, String> params = new HashMap<>();
                        params.put("id", User.id);

                        aQuery.ajax("http://52.79.134.200/signup/id/check", params, String.class, new AjaxCallback<String>() {
                            @Override
                            public void callback(String url, String response, AjaxStatus status) {
                                int statusCode = status.getCode();
                                if (statusCode == 201) {
                                    // 미중복
                                    userId.setTextColor(Color.rgb(111, 186, 119));
                                    User.idChecked = true;
                                } else {
                                    // 중복
                                    userId.setTextColor(Color.rgb(252, 113, 80));
                                }
                            }
                        });
                    } else {
                        // EditText가 비어있을 때
                        User.idChecked = false;
                    }
                } else {
                    // Focus가 넘어올 때
                }
            }
        });

        passwordConfirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                User.password = userPassword.getText().toString();
                String confirm = s.toString();

                if (User.password.equals(confirm)) {
                    userPassword.setTextColor(Color.rgb(111, 186, 119));
                    passwordConfirm.setTextColor(Color.rgb(111, 186, 119));
                    User.passwordConfirmed = true;
                } else {
                    passwordConfirm.setTextColor(Color.rgb(252, 113, 80));
                    User.passwordConfirmed = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (User.emailCertified && User.nameChecked && User.idChecked && User.passwordConfirmed) {
                    Map<String, String> params = new HashMap<>();

                    params.put("email", User.email);
                    params.put("name", User.name);
                    params.put("id", User.id);
                    params.put("password", User.password);
                    params.put("registration_id", firebase.getFirebaseToken());

                    aQuery.ajax("http://52.79.134.200/signup", params, String.class, new AjaxCallback<String>() {
                        @Override
                        public void callback(String url, String response, AjaxStatus status) {
                            int statusCode = status.getCode();
                            if (statusCode == 201) {
                                finish();
                                Intent intent = new Intent(getApplicationContext(), SignIn.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(), "회원가입 실패", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    // 체크가 모두 안되어 있을 경우
                }
            }
        });
    }
}

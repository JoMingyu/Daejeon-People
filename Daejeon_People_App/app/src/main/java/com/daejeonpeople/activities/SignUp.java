package com.daejeonpeople.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.daejeonpeople.R;
import com.daejeonpeople.support.firebase.Firebase;
import com.daejeonpeople.support.views.ColorManager;
import com.daejeonpeople.support.views.SnackbarManager;
import com.daejeonpeople.valueobject.UserInSignup;

import java.util.HashMap;
import java.util.Map;
// 민지
// Modified by JoMingyu

public class SignUp extends Activity {
    private AQuery aQuery;
    private Firebase firebase;

    private Button submitBtn;
    private Button emailCertifiedBtn;

    private EditText userName;
    private EditText userId;
    private EditText userPassword;
    private EditText passwordConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        aQuery = new AQuery(getApplicationContext());
        firebase = new Firebase();

        submitBtn = (Button) findViewById(R.id.submitButton);
        emailCertifiedBtn = (Button) findViewById(R.id.emailButton);

        userName = (EditText) findViewById(R.id.inputName);
        userId = (EditText) findViewById(R.id.inputId);
        userPassword = (EditText) findViewById(R.id.inputPassword);
        passwordConfirm = (EditText) findViewById(R.id.inputPasswordConfirm);

        if(UserInSignup.emailCertified) {
            // 이메일 인증이 완료됐다면 버튼 컬러 변경
            emailCertifiedBtn.setTextColor(ColorManager.successColor);
            SnackbarManager.createCancelableSnackbar(getWindow().getDecorView().getRootView(), "이메일 인증 완료").show();
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
                    userName.setTextColor(ColorManager.successColor);
                    UserInSignup.name = userName.getText().toString();
                    UserInSignup.nameChecked = true;
                } else {
                    // 포커스가 다시 바뀌는 경우, 이름이 비어있는 경우를 방지
                    UserInSignup.nameChecked = false;
                }
            }
        });

        userName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                userName.setTextColor(Color.BLACK);
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        userId.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                UserInSignup.id = userId.getText().toString();
                if (!hasFocus) {
                    // Focus가 넘어갈 때
                    if(!UserInSignup.id.isEmpty()) {
                        // EditText가 비어있지 않을 때

                        Map<String, String> params = new HashMap<>();
                        params.put("id", UserInSignup.id);

                        aQuery.ajax("http://52.79.134.200/signup/id/check", params, String.class, new AjaxCallback<String>() {
                            @Override
                            public void callback(String url, String response, AjaxStatus status) {
                                int statusCode = status.getCode();
                                if (statusCode == 201) {
                                    // 미중복
                                    userId.setTextColor(ColorManager.successColor);
                                    UserInSignup.idChecked = true;
                                } else {
                                    // 중복
                                    userId.setTextColor(ColorManager.failureColor);
                                }
                            }
                        });
                    } else {
                        // EditText가 비어있을 때
                        UserInSignup.idChecked = false;
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
                UserInSignup.password = userPassword.getText().toString();
                String confirm = s.toString();

                if (UserInSignup.password.equals(confirm)) {
                    userPassword.setTextColor(ColorManager.successColor);
                    passwordConfirm.setTextColor(ColorManager.successColor);
                    UserInSignup.passwordConfirmed = true;
                } else {
                    passwordConfirm.setTextColor(ColorManager.failureColor);
                    UserInSignup.passwordConfirmed = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserInSignup.emailCertified && UserInSignup.nameChecked && UserInSignup.idChecked && UserInSignup.passwordConfirmed) {
                    Map<String, String> params = new HashMap<>();

                    params.put("email", UserInSignup.email);
                    params.put("name", UserInSignup.name);
                    params.put("id", UserInSignup.id);
                    params.put("password", UserInSignup.password);
                    params.put("registration_id", firebase.getFirebaseToken());

                    aQuery.ajax("http://52.79.134.200/signup", params, String.class, new AjaxCallback<String>() {
                        @Override
                        public void callback(String url, String response, AjaxStatus status) {
                            int statusCode = status.getCode();
                            if (statusCode == 201) {
                                SnackbarManager.createCancelableSnackbar(getWindow().getDecorView().getRootView(), "회원가입 성공!").show();
                                finish();
                                Intent intent = new Intent(getApplicationContext(), SignIn.class);
                                startActivity(intent);
                            } else {
                                SnackbarManager.createCancelableSnackbar(getWindow().getDecorView().getRootView(), "회원가입 실패").show();
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

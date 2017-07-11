package com.daejeonpeople.activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.daejeonpeople.R;
import com.daejeonpeople.activities.base.BaseActivity;
import com.daejeonpeople.support.views.ColorManager;
import com.daejeonpeople.support.views.SnackbarManager;
import com.daejeonpeople.valueobject.UserInSignup;

import java.util.HashMap;
import java.util.Map;

// 민지
// Modified by JoMingyu

public class Email_Certified extends BaseActivity {
    private Map<String, Object> params = new HashMap<>();

    private EditText email;
    private Button confirmButton;
    private AQuery aQuery;

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.email_certified);

        aQuery = new AQuery(getApplicationContext());

        confirmButton = (Button) findViewById(R.id.confirmBtn);
        email = (EditText) findViewById(R.id.email);

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 이메일 EditText가 수정되면 다시 검은색이 되도록
                email.setTextColor(Color.BLACK);
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if(email.getText().toString().isEmpty()) {
                    SnackbarManager.createCancelableSnackbar(v, "이메일을 확인하세요.").show();
                }

                params.put("email", email.getText().toString());
                System.out.println(params);

                aQuery.ajax("http://52.79.134.200/signup/email/demand", params, String.class, new AjaxCallback<String>() {
                    @Override
                    public void callback(String url, String response, AjaxStatus status) {
                        System.out.println(status.getCode());
                        if (status.getCode() == 201) {
                            email.setTextColor(ColorManager.successColor);
                            // 이메일 텍스트 컬러를 바꿈

                            ShowDialog();
                        } else {
                            email.setTextColor(ColorManager.failureColor);

                            SnackbarManager.createCancelableSnackbar(v, "이미 존재하는 이메일입니다.").show();
                        }
                    }
                });
            }
        });
    }

    private void ShowDialog()
    {
        final LayoutInflater dialog = LayoutInflater.from(this);
        final View dialogLayout = dialog.inflate(R.layout.dialog_email_certified_input, null);
        final Dialog myDialog = new Dialog(this);

        myDialog.setTitle("이메일 인증");
        myDialog.setContentView(dialogLayout);
        myDialog.show();

        final Button okBtn = (Button) dialogLayout.findViewById(R.id.okBtn);
        final Button cancelBtn = (Button) dialogLayout.findViewById(R.id.cancelBtn);
        final EditText checkCode = (EditText) dialogLayout.findViewById(R.id.checkCode);

        checkCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkCode.setTextColor(Color.BLACK);
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        okBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(final View v)
            {
                HashMap<String, String> params = new HashMap<>();
                params.put("email", email.getText().toString());
                params.put("code", checkCode.getText().toString());

                aQuery.ajax("http://52.79.134.200/signup/email/verify", params, String.class, new AjaxCallback<String>(){
                    @Override
                    public void callback(String url, String response, AjaxStatus status) {
                        System.out.println(status.getCode());
                        if(status.getCode() == 201){
                            UserInSignup.email = email.getText().toString();
                            UserInSignup.emailCertified = true;
                            // 인증 완료되었음을 표시

                            SnackbarManager.createCancelableSnackbar(getWindow().getDecorView().getRootView(), "이메일 인증 완료").show();
                            myDialog.cancel();
                            finish();
                            Intent intent = new Intent(getApplicationContext(), SignUp.class);
                            startActivity(intent);
                        } else {
                            UserInSignup.emailCertified = false;

                            checkCode.setTextColor(ColorManager.failureColor);
                            SnackbarManager.createCancelableSnackbar(getWindow().getDecorView().getRootView(), "인증번호가 맞지 않습니다.").show();
                        }
                    }
                }.method(AQuery.METHOD_POST));
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                myDialog.cancel();
            }
        });
    }
}

